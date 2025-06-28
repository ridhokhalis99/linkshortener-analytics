package com.ridhokhalis.linkshortenerAnalytics.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnalyticsEvent {
    private String shortCode;
    private String ipAddress;
    private String userAgent;
    private String referrer;
    private LocalDateTime timestamp;
}
