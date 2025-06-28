package com.ridhokhalis.linkshortenerAnalytics.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeoIpService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String IPINFO_URL = "https://ipinfo.io/{ip}/json?token={token}";

    @Value("${ipinfo.token}")
    private String ipinfoToken;

    public String getCountryByIp(String ip) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    IPINFO_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    ip, ipinfoToken
            );
            Map<String, Object> body = response.getBody();
            return body != null ? (String) body.get("country") : null;
        } catch (Exception e) {
            return null;
        }
    }
}
