package com.mainlab.service;

import com.google.common.collect.Lists;
import com.mainlab.common.OperationService;
import com.mainlab.common.OperationType;
import com.mainlab.common.OperationUnit;
import com.mainlab.model.UserApp;
import com.mainlab.model.UserAppRequest;
import com.mainlab.model.UserAppShortcut;
import com.mainlab.model.exception.BaseRuntimeException;
import com.mainlab.model.exception.ErrorCode;
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

    public void addUserApp(UserAppRequest userAppRequest) {
        //Write Operation -> Authorized Users only can do
        String queryUserId = userService.getOperationUserId(OperationType.WRITE);
        //TODO: Check in every service or Implement it in Interceptor

        UserApp userApp = userAppRequest.getUserApp();
        userApp.setUserId(queryUserId);

        List<OperationUnit> operationUnitList = Lists.newLinkedList();
        operationUnitList.add(() -> userAppRepository.insertUserApp(userApp));
        operationUnitList.add(() ->
                userAppShortcutRepository.insertUserAppShortcut(
                        new UserAppShortcut(queryUserId, userApp.getAppId(), userAppRequest.getThumbnail())));

        operationService.operate(queryUserId, operationUnitList);
    }
}
