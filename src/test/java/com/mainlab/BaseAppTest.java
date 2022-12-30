package com.mainlab;

import com.mainlab.common.OperationService;
import com.mainlab.repository.*;
import com.mainlab.service.*;
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

    @Autowired
    protected ContentService contentService;

    @Autowired
    protected ContentLinkRepository contentLinkRepository;

    @Autowired
    protected UserBlogService userBlogService;
    @Autowired
    protected UserBlogRepository userBlogRepository;
}
