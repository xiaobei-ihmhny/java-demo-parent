package com.xiaobei.java.demo.ehcache.jsr107;

import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.util.concurrent.TimeUnit;

/**
 * 第一种比较完整的配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-17 06:50:50
 */
//@Configuration
public class JSR107EhcacheConfiguration {

    @Bean(name = "jCacheCacheManager")
    public JCacheCacheManager ehCacheManagerFactoryBean(CacheManager jCacheManager) {
        JCacheCacheManager bean = new JCacheCacheManager();
        bean.setCacheManager(jCacheManager);
        return bean;
    }

    @Bean(name = "jCacheManager")
    public CacheManager jCacheManager()  {
        CachingProvider cachingProvider
                = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
        return cachingProvider.getCacheManager();
    }

    @Bean(name = "jCache")
    public Cache<Long, String> jCache(CacheManager jCacheManager) {
        MutableConfiguration<Long, String> configuration
                = new MutableConfiguration<Long, String>()
                .setTypes(Long.class, String.class)
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 10)));
        return jCacheManager.createCache("jCache", configuration);
    }
}