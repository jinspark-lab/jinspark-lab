package com.mainlab.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
public class CacheConfig extends CachingConfigurerSupport {

    /***
     * Composite Cache Manager for EhCache & Redis
     * How to Use : @Cacheable(value = "firstCache", key = "#word", cacheManager = "ehCacheCacheManager")
     * @return
     */
    @Override
    @Bean("cacheManager")
    public CacheManager cacheManager() {
        List<CacheManager> cacheManagerList = new LinkedList<>();
        cacheManagerList.add(new EhCacheCacheManager(Objects.requireNonNull(ehCacheManager().getObject())));
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(cacheManagerList);
        compositeCacheManager.setFallbackToNoOpCache(false);
        return compositeCacheManager;
    }

    @Bean("ehcacheManager")
    public EhCacheManagerFactoryBean ehCacheManager() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);      //Share cache at the Class Loader level
        return ehCacheManagerFactoryBean;
    }
}
