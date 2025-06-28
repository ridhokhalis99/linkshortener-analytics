package com.ridhokhalis.linkshortenerAnalytics.repository;

import com.ridhokhalis.linkshortenerAnalytics.entity.AnalyticsEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEventEntity, Long> {
}
