package com.tingcore.cdc.configuration;

import com.tingcore.cdc.crm.repository.AttributeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author palmithor
 * @since 2017-11-10
 */
@Component
public class CacheScheduler {

    private static final int HOUR_AS_MILLISECONDS = 1000 * 60 * 60;
    private final AttributeRepository attributeRepository;

    public CacheScheduler(final AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Scheduled(fixedRate = 3 * HOUR_AS_MILLISECONDS, initialDelay = 4000)
    public void resetCache() {
        attributeRepository.resetCache();
    }

}
