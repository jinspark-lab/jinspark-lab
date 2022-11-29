package com.mainlab;

import com.mainlab.common.OperationService;
import com.mainlab.repository.SharableRepository;
import com.mainlab.repository.UserAppRepository;
import com.mainlab.repository.UserAppShortcutRepository;
import com.mainlab.service.EnvironmentService;
import com.mainlab.service.StorageService;
import com.mainlab.service.UserAppService;
import com.mainlab.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

public abstract class BaseAppTest {

    @Autowired
    protected UserProfileService userProfileService;

    @Autowired
    protected CacheManager cacheManager;

    @Autowired
    protected StorageService storageService;

    @Autowired
    protected UserAppService userAppService;

    @Autowired
    protected OperationService operationService;

    @Autowired
    protected UserAppRepository userAppRepository;

    @Autowired
    protected UserAppShortcutRepository userAppShortcutRepository;

    @Autowired
    protected SharableRepository sharableRepository;

    @Autowired
    protected EnvironmentService environmentService;
}
