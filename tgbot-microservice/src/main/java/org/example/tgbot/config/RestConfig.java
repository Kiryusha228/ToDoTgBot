package org.example.tgbot.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                //.errorHandler()
                .build();
    }
}
