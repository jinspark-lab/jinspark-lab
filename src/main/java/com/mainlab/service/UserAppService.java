package com.mainlab.service;

import com.google.common.collect.Lists;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserAppRequest;
import com.mainlab.model.UserAppResponse;
import com.mainlab.model.UserAppShortcut;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
import com.mainlab.model.exception.ErrorCodes;
import com.mainlab.repository.UserAppRepository;
import com.mainlab.repository.UserAppShortcutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;
    @Autowired
    private UserAppShortcutRepository userAppShortcutRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OperationService operationService;

    public List<UserAppShortcut> getUserAppShortcutList() {
        String queryUserId = userService.getOperationUserId(OperationType.READ);
        return userAppShortcutRepository.selectUserAppShortcutList(queryUserId);
    }

    public UserApp getUserAppDetail(String appId) {
        String queryUserId = userService.getOperationUserId(OperationType.READ);
        return Optional.ofNullable(userAppRepository.selectUserApp(queryUserId, appId))
                .orElseThrow(() -> new BaseRuntimeException("User App does not exists. userId=" + queryUserId + ", appId=" + appId
                        , ErrorCode.USER_APP_NOT_EXIST));
    }

    public UserAppResponse getUserAppAdminDetail(String appId) {
        UserApp userApp = getUserAppDetail(appId);
        UserAppShortcut userAppShortcut = getUserAppShortcutList().stream()
                .filter(userAppShortcut1 -> userAppShortcut1.getAppId().equals(appId)).findFirst()
                .orElseThrow(() -> new BaseRuntimeException("User App Shortcut does not exists. appId=" + appId,
                        ErrorCode.USER_APP_SHORTCUT_NOT_EXIST));
        return new UserAppResponse(userApp, userAppShortcut.getThumbnailUrl());
    }

    private void validateUserAppRequest(UserAppRequest userAppRequest) {
        ErrorCodes.checkCondition(Optional.ofNullable(userAppRequest.getUserApp().getAppId()).isPresent(), ErrorCode.USER_APP_INVALID_REQUEST, "Invalid User App Request. appId=" + userAppRequest.getUserApp().getAppId());
        ErrorCodes.checkCondition(!userAppRequest.getUserApp().getAppId().equals(""), ErrorCode.USER_APP_INVALID_REQUEST, "Invalid User App Request. appId=" + userAppRequest.getUserApp().getAppId());
    }

    public void addUserApp(UserAppRequest userAppRequest) {
        //Write Operation -> Authorized Users only can do
        validateUserAppRequest(userAppRequest);
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);

        UserApp userApp = userAppRequest.getUserApp();

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppRepository.insertUserApp(queryUserId, userApp));
        operationUnitList.add(() ->
                userAppShortcutRepository.insertUserAppShortcut(queryUserId,
                        new UserAppShortcut(queryUserId, userApp.getAppId(), userAppRequest.getThumbnailUrl())));

        operationService.operate(queryUserId, operationUnitList);
    }

    public void updateUserApp(UserAppRequest userAppRequest) {
        validateUserAppRequest(userAppRequest);

        String queryUserId = userService.getOperationUserId(OperationType.WRITE);
        UserAppShortcut userAppShortcut = new UserAppShortcut(queryUserId, userAppRequest.getUserApp().getAppId(), userAppRequest.getThumbnailUrl());

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppRepository.updateUserApp(queryUserId, userAppRequest.getUserApp()));
        operationUnitList.add(() -> userAppShortcutRepository.updateUserAppShortcut(queryUserId, userAppShortcut));
        operationService.operate(queryUserId, operationUnitList);
    }

    public void deleteUserApp(String appId) {
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppShortcutRepository.deleteUserAppShortcut(queryUserId, appId));
        operationUnitList.add(() -> userAppRepository.deleteUserApp(queryUserId, appId));
        operationService.operate(queryUserId, operationUnitList);
    }
}
