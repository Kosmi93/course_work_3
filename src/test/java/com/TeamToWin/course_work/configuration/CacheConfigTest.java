package com.TeamToWin.course_work.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(CacheConfig.class)
public class CacheConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testCacheManagerBeanCreation() {
        CacheManager cacheManager = applicationContext.getBean(CacheManager.class);

        assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);

        CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
        assertThat(caffeineCacheManager.getCacheNames()).contains("getUserRecommendation");

    }
}
