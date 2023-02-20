package com.mainlab.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomCacheResolver extends SimpleCacheResolver {

    public CustomCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        // Return Cache which has cached object per cache key
        return ((CacheOperation) context.getOperation()).getCacheNames().stream()
                .map(cacheName -> getCacheManager().getCache(cacheName))
                .filter(cache -> Optional.ofNullable(cache).isPresent()).collect(Collectors.toList());
    }
}
