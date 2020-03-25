package com.xiaobei.java.demo.ehcache.jsr107;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 第二种简化的配置
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-17 06:50:50
 */
@Configuration
public class JSR107EhcacheConfiguration2 implements InitializingBean {

    @Bean(name = "jCacheCacheManager")
    public JCacheCacheManager ehCacheManagerFactoryBean() {
        return new JCacheCacheManager();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MutableConfiguration<Long, String> configuration
                = new MutableConfiguration<Long, String>()
                .setTypes(Long.class, String.class)
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 1)));
        JCacheCacheManager jCacheCacheManager = ehCacheManagerFactoryBean();
        // 创建jCache缓存
        jCacheCacheManager.getCacheManager().createCache("jCache", configuration);
        jCacheCacheManager.getCacheManager().createCache("failures", configuration);
    }
}