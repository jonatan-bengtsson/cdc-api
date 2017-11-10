package com.tingcore.cdc.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CacheConfiguration.class);

    public static final String USER_SERVICE_ATTRIBUTES = "userServiceAttributes";

    /**
     * Temporary in memory cache which is currently only used for user service attributes
     */
    @Bean
    public CacheManager concurrentMapCacheManager() {
        LOG.debug("Cache manager is concurrentMapCacheManager");
        return new ConcurrentMapCacheManager(USER_SERVICE_ATTRIBUTES);
    }
}
