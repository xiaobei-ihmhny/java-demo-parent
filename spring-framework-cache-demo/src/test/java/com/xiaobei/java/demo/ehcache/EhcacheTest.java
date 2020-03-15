package com.xiaobei.java.demo.ehcache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-14 20:29:29
 */
public class EhcacheTest {

    @Test
    public void segment1() {
        CacheManager cacheManager =
                CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();
        cacheManager.init();
        Cache<Long, String> preCache
                = cacheManager.getCache("preConfigured", Long.class, String.class);
        preCache.put(1L, "xiaobei");
        String value = preCache.get(1L);
        System.out.printf("获取结果为 %s \n", value);
        cacheManager.removeCache("preConfigured");
        cacheManager.close();
    }

    /**
     * 因为 {@link CacheManager} 实现了 {@link java.io.Closeable}
     * 故可以使用 {@code try-with-resources}，以保持代码简洁
     */
    @Test
    public void segment2() {
        try(CacheManager cacheManager
                    = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache(
                        "preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build(true)) {
            Cache<Long, String> preCache
                    = cacheManager.getCache("preConfigured", Long.class, String.class);
            preCache.put(1L, "huihui");
            String value = preCache.get(1L);
            System.out.printf("value值为 %s \n", value);
        }
    }
}