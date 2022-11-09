package com.mainlab.service;

import com.google.api.client.util.Lists;
import com.google.common.collect.ImmutableList;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.RoleType;
import com.mainlab.model.UserInfo;
import com.mainlab.model.UserRole;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.exception.ErrorCodes;
import com.mainlab.model.exception.MalformedRequestException;
import com.mainlab.model.exception.NotSupportedException;
import com.mainlab.repository.UserInfoRepository;
import com.mainlab.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${service.master.email}")
    private String masterEmail;

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private OperationService operationService;

    public UserInfo loadUserInfo(String userId) {
        UserInfo userInfo = userInfoRepository.selectUserInfo(userId);
        if (Optional.ofNullable(userInfo).isPresent()) {
            userInfo.setUserRoleList(userRoleRepository.selectUserRoleList(userId));
        }
        return userInfo;
    }

    public UserInfo loadUserInfoByToken(String refreshToken) {
        UserInfo userInfo = userInfoRepository.selectUserInfoByRefreshToken(refreshToken);
        if (Optional.ofNullable(userInfo).isPresent()) {
            userInfo.setUserRoleList(userRoleRepository.selectUserRoleList(userInfo.getUserId()));
        }
        return userInfo;
    }

    public String getOperationUserId(OperationType operationType) {
        UserInfo userInfo = getUserContextHolder();
        if (operationType.equals(OperationType.READ)) {
            return userInfo.getUserLevel().equals(RoleType.GUEST) ? masterEmail : userInfo.getUserId();
        } else {
            ErrorCodes.checkCondition(!userInfo.getUserLevel().equals(RoleType.GUEST), ErrorCode.MALFORMED_REQUEST, "Guest cannot operate Write operation", MalformedRequestException.class);
            return userInfo.getUserId();
        }
    }

    public UserInfo createAndGetUserInfo(String userId, String refreshToken) {
        List<OperationUnit> operationUnitList = Lists.newArrayList();
        operationUnitList.add(() -> userInfoRepository.insertUserInfo(userId, refreshToken));
        operationUnitList.add(() -> userRoleRepository.insertUserRoleList(userId, ImmutableList.of(new UserRole(userId, RoleType.GUEST))));
        operationService.operate(userId, operationUnitList);
        return loadUserInfo(userId);
    }

    public void updateRefreshToken(String userId, String refreshToken) {
        List<OperationUnit> operationUnitList = Lists.newArrayList();
        operationUnitList.add(() -> userInfoRepository.updateUserInfo(userId, refreshToken));
        operationService.operate(userId, operationUnitList);
    }

    public UserInfo getUserContextHolder() {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loadUserInfo(principal);
    }

    public void setUserContextHolder(UserInfo userInfo) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userInfo.getUserId(),
                userInfo.getRefreshToken(),
                userInfo.getRoleTypeList().stream().map(RoleType::toAuthority).collect(Collectors.toList())));
    }
}
