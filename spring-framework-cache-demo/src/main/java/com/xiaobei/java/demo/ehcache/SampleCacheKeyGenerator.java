package com.xiaobei.java.demo.ehcache;

import javax.cache.annotation.CacheKeyGenerator;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.GeneratedCacheKey;
import java.lang.annotation.Annotation;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-22 10:58:58
 */
public class SampleCacheKeyGenerator implements CacheKeyGenerator {


    @Override
    public GeneratedCacheKey generateCacheKey(
            CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext) {
        return null;
    }
}