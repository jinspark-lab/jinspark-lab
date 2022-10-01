package com.mainlab.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.collect.ImmutableList;
import com.mainlab.common.ObjectConvertService;
import com.mainlab.model.RoleType;
import com.mainlab.model.UserInfo;
import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.login.AuthEnvironmentModel;
import com.mainlab.model.login.JwtResponse;
import com.mainlab.model.login.UserToken;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Value("${google.auth.clientid}")
    private String googleClientId;

    @Value("${jinsparklab.token.issuer}")
    private String authIssuer;
    @Value("${jinsparklab.token.secret}")
    private String authSecret;

    @Autowired
    private UserService userService;

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private ObjectConvertService objectConvertService;

    private String getAuthIssuer() {
        if (Optional.ofNullable(authIssuer).isPresent() && !authIssuer.equals("") && !authIssuer.equals(".")) {
            return authIssuer;
        }
        String authInfo = environmentService.getStringValue("jinsparklab/app-auth");
        AuthEnvironmentModel authEnvironmentModel = objectConvertService.stringToObj(authInfo, AuthEnvironmentModel.class);
        return authEnvironmentModel.getIssuer();
    }

    private String getAuthSecret() {
        if (Optional.ofNullable(authSecret).isPresent() && !authSecret.equals("") && !authSecret.equals(".")) {
            return authSecret;
        }
        String authInfo = environmentService.getStringValue("jinsparklab/app-auth");
        AuthEnvironmentModel authEnvironmentModel = objectConvertService.stringToObj(authInfo, AuthEnvironmentModel.class);
        return authEnvironmentModel.getSecret();
    }

    public JwtResponse loginGoogle(String credential) {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(ImmutableList.of(googleClientId))
                .build();
        GoogleIdToken idToken = null;
        UserToken token = null;
        try {
            idToken = verifier.verify(credential);
            GoogleIdToken.Payload payload = idToken.getPayload();

            if (payload.getEmailVerified()) {
                UserInfo userInfo = userService.loadUserInfo(payload.getEmail());
                if (Optional.ofNullable(userInfo).isPresent()) {

                    //If refresh Token is expired, need to refresh "Refresh token".
                    if (isExpired(userInfo.getRefreshToken())) {
                        String refreshToken = generateRefreshToken(payload.getEmail(), userInfo.getRoleTypeList());
                        userService.updateRefreshToken(userInfo.getUserId(), refreshToken);
                        userInfo = userService.loadUserInfo(userInfo.getUserId());
                    }
                    String accessToken = generateAccessToken(userInfo);
                    token = new UserToken(accessToken, userInfo.getRefreshToken());
                } else {
                    UserInfo newUserInfo = registerNewUser(payload.getEmail());
                    String accessToken = generateAccessToken(newUserInfo);
                    token = new UserToken(accessToken, newUserInfo.getRefreshToken());
                }
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        if (Optional.ofNullable(token).isPresent()) {
            return new JwtResponse(token);
        }
        throw new BaseRuntimeException("Unable to login via Google.", ErrorCode.UNAUTHORIZED);
    }

    public UserInfo registerNewUser(String email) {
        String refreshToken = generateRefreshToken(email, ImmutableList.of(RoleType.GUEST));
        return userService.createAndGetUserInfo(email, refreshToken);
    }

    public JwtResponse refreshUserAccess(String refreshToken) {
        UserInfo userInfo = userService.loadUserInfoByToken(refreshToken);

        try {
            DecodedJWT jwt = decodeJWT(userInfo.getRefreshToken());
            if (!isExpired(jwt)) {
                String newAccessToken = generateAccessToken(userInfo);
                return new JwtResponse(new UserToken(newAccessToken, userInfo.getRefreshToken()));
            }
            throw new BaseRuntimeException("Unexpected JWT error", ErrorCode.INTERNAL_ERROR);
        } catch (TokenExpiredException e) {
            System.out.println(e.getMessage());
            String newRefreshToken = generateRefreshToken(userInfo.getUserId(), userInfo.getRoleTypeList());
            userService.updateRefreshToken(userInfo.getUserId(), newRefreshToken);
            String newAccessToken = generateAccessToken(userService.loadUserInfo(userInfo.getUserId()));
            return new JwtResponse(new UserToken(newAccessToken, newRefreshToken));
        }
    }

    private String generateAccessToken(UserInfo userInfo) {
        Algorithm algorithm = Algorithm.HMAC256(getAuthSecret());
        DateTime issued = DateTime.now();

        return JWT.create()
                .withSubject(userInfo.getUserId())
                .withClaim("userId", userInfo.getUserId())
                .withClaim("role", userInfo.getRoleTypeList().stream().map(RoleType::toString).collect(Collectors.toList()))
                .withIssuedAt(new Date(issued.getMillis()))
                .withExpiresAt(new Date(issued.plusMinutes(1).getMillis()))
                .withIssuer(getAuthIssuer())
                .sign(algorithm);
    }

    private String generateRefreshToken(String userId, List<RoleType> roleTypeList) {
        Algorithm algorithm = Algorithm.HMAC256(getAuthSecret());
        DateTime issued = DateTime.now();

        return JWT.create()
                .withSubject(userId)
                .withClaim("userId", userId)
                .withClaim("role", roleTypeList.stream().map(RoleType::toString).collect(Collectors.toList()))
                .withIssuedAt(new Date(issued.getMillis()))
                .withExpiresAt(new Date(issued.plusMinutes(60).getMillis()))
                .withIssuer(getAuthIssuer())
                .sign(algorithm);
    }

    public UserInfo loadUserFromAuthToken(String token) {
        try {
            DecodedJWT jwt = decodeJWT(token);
            // Need to decode JWT.
            if (jwt.getIssuer().equals(getAuthIssuer()) && !isExpired(jwt)) {
                String userId = jwt.getClaim("userId").asString();
                return userService.loadUserInfo(userId);
            }
            throw new AuthorizationException("Issuer Failed to check. ", ErrorCode.UNAUTHORIZED);
        } catch (TokenExpiredException e) {
            throw new AuthorizationException("Access Token has been Expired. ", ErrorCode.UNAUTHORIZED);
        } catch (Exception e) {
            throw new AuthorizationException(e.getMessage(), ErrorCode.UNAUTHORIZED);
        }
    }

    /***
     * Return true if JWT is valid
     * @param jwt
     * @return
     */
    public boolean isExpired(DecodedJWT jwt) {
        long now = DateTime.now().getMillis();
        return !(jwt.getIssuedAt().getTime() < now && now < jwt.getExpiresAt().getTime());
    }

    public boolean isExpired(String token) {
        try {
            return isExpired(decodeJWT(token));
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    private DecodedJWT decodeJWT(String token) throws TokenExpiredException {
        Algorithm algorithm = Algorithm.HMAC256(getAuthSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(getAuthIssuer())
                .build();
        return verifier.verify(token);
    }
}
