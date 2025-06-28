package com.ridhokhalis.linkshortenerAnalytics.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridhokhalis.linkshortenerAnalytics.dto.AnalyticsEvent;
import com.ridhokhalis.linkshortenerAnalytics.entity.AnalyticsEventEntity;
import com.ridhokhalis.linkshortenerAnalytics.repository.AnalyticsEventRepository;
import com.ridhokhalis.linkshortenerAnalytics.service.GeoIpService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsConsumer.class);
    private final ObjectMapper objectMapper;
    private final GeoIpService geoIpService;
    private final AnalyticsEventRepository analyticsEventRepository;

    public AnalyticsConsumer(
            ObjectMapper objectMapper,
            GeoIpService geoIpService,
            AnalyticsEventRepository analyticsEventRepository
    ) {
        this.objectMapper = objectMapper;
        this.geoIpService = geoIpService;
        this.analyticsEventRepository = analyticsEventRepository;
    }

    @KafkaListener(topics = "analytics-events", groupId = "analytics-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            String json = record.value();
            AnalyticsEvent event = objectMapper.readValue(json, AnalyticsEvent.class);
            String country = geoIpService.getCountryByIp(event.getIpAddress());

            AnalyticsEventEntity entity = AnalyticsEventEntity.builder()
                    .shortCode(event.getShortCode())
                    .timestamp(event.getTimestamp())
                    .ipAddress(event.getIpAddress())
                    .userAgent(event.getUserAgent())
                    .referrer(event.getReferrer())
                    .country(country)
                    .build();

            analyticsEventRepository.save(entity);

        } catch (Exception e) {
            logger.error("Failed to process analytics event", e);
        }
    }
}
