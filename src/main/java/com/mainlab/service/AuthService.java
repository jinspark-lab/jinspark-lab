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
import com.mainlab.model.RoleType;
import com.mainlab.model.UserInfo;
import com.mainlab.model.exception.AuthorizationException;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
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

    public JwtResponse loginGoogle(String credential) {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(List.of(googleClientId))
                .build();
        GoogleIdToken idToken = null;
        UserToken token = null;
        try {
            idToken = verifier.verify(credential);
            GoogleIdToken.Payload payload = idToken.getPayload();

            if (payload.getEmailVerified()) {
                UserInfo userInfo = userService.loadUserInfo(payload.getEmail());
                if (Optional.ofNullable(userInfo).isPresent()) {
                    String accessToken = generateAccessToken(userInfo);
                    token = new UserToken(accessToken, userInfo.getRefreshToken());
                } else {
                    String refreshToken = generateRefreshToken(payload.getEmail(), List.of(RoleType.GUEST));
                    UserInfo newUserInfo = userService.createAndGetUserInfo(payload.getEmail(), refreshToken);
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

    public JwtResponse refreshUserAccess(String refreshToken) {
        UserInfo userInfo = userService.loadUserInfoByToken(refreshToken);
        DecodedJWT jwt = decodeJWT(userInfo.getRefreshToken());

        if (!verifyExpiration(jwt)) {
            String newRefreshToken = generateRefreshToken(userInfo.getUserId(), userInfo.getRoleTypeList());
            userService.updateRefreshToken(userInfo.getUserId(), newRefreshToken);
            String newAccessToken = generateAccessToken(userService.loadUserInfo(userInfo.getUserId()));
            return new JwtResponse(new UserToken(newAccessToken, newRefreshToken));
        } else {
            String newAccessToken = generateAccessToken(userInfo);
            return new JwtResponse(new UserToken(newAccessToken, userInfo.getRefreshToken()));
        }
    }

    private String generateAccessToken(UserInfo userInfo) {
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        DateTime issued = DateTime.now();

        return JWT.create()
                .withSubject(userInfo.getUserId())
                .withClaim("userId", userInfo.getUserId())
                .withClaim("role", userInfo.getRoleTypeList().stream().map(RoleType::toString).collect(Collectors.toList()))
                .withIssuedAt(new Date(issued.getMillis()))
                .withExpiresAt(new Date(issued.plusMinutes(10).getMillis()))
                .withIssuer(authIssuer)
                .sign(algorithm);
    }

    private String generateRefreshToken(String userId, List<RoleType> roleTypeList) {
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        DateTime issued = DateTime.now();

        return JWT.create()
                .withSubject(userId)
                .withClaim("userId", userId)
                .withClaim("role", roleTypeList.stream().map(RoleType::toString).collect(Collectors.toList()))
                .withIssuedAt(new Date(issued.getMillis()))
                .withExpiresAt(new Date(issued.plusMinutes(60).getMillis()))
                .sign(algorithm);
    }

    public UserInfo loadUserFromAuthToken(String token) {
        try {
            DecodedJWT jwt = decodeJWT(token);
            // Need to decode JWT.
            if (jwt.getIssuer().equals(authIssuer) && verifyExpiration(jwt)) {
                String userId = jwt.getClaim("userId").asString();
                return userService.loadUserInfo(userId);
            }
            throw new AuthorizationException("Issuer Failed to check. ", ErrorCode.UNAUTHORIZED);
        } catch (TokenExpiredException e) {
            throw new AuthorizationException("Token has been Expired. ", ErrorCode.UNAUTHORIZED);
        } catch (Exception e) {
            throw new AuthorizationException(e.getMessage(), ErrorCode.UNAUTHORIZED);
        }
    }

    /***
     * Return true if JWT is valid
     * @param jwt
     * @return
     */
    private boolean verifyExpiration(DecodedJWT jwt) {
        // JWT handles time as second, so it is necessary to compare it with second, not millisecond.
        long nowSecond = DateTime.now().getMillis();
        return jwt.getIssuedAt().getTime() < nowSecond && nowSecond < jwt.getExpiresAt().getTime();
    }

    private DecodedJWT decodeJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(authSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(authIssuer)
                .build();
        return verifier.verify(token);
    }
}
