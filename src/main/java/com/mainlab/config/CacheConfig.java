package com.mainlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
public class CacheConfig extends CachingConfigurerSupport {

    @Value("${spring.profiles.active}")
    private String profile;
    @Value("${redis.endpoint.url}")
    private String cacheUrl;
    @Autowired
    private SecretsManagerClient secretsManagerClient;

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
        cacheManagerList.add(redisCacheManager(redisConnectionFactory()));
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

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        String endpoint = cacheUrl;
        if (profile.equals("prod")) {
            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId("jinsparklab-cache-endpoint")
                    .build();
            GetSecretValueResponse getSecretValueResponse;
            try {
                getSecretValueResponse = secretsManagerClient.getSecretValue(getSecretValueRequest);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
            endpoint = getSecretValueResponse.secretString();
        }
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(endpoint);
        redisStandaloneConfiguration.setPort(6379);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.create(connectionFactory);
    }

    @Override
    public CacheResolver cacheResolver() {
        return new CustomCacheResolver(cacheManager());
    }
}
