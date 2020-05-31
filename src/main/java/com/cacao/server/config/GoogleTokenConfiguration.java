package com.cacao.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Configuration
public class GoogleTokenConfiguration {

    @Value("${token.google.clientId}")
    private String clientId;
    @Value("${token.google.clientSecret}")
    private String clientSecret;
    @Value("${token.google.redirectUrl}")
    private String redirectUrl;

    @Bean("googleTokenParams")
    public Map<String, String> googleTokenParams() {
        return Collections.unmodifiableMap(
                Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "redirect_uri", redirectUrl));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
