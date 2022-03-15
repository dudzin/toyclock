package com.daro.toyclock.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebhookSender {

    private final RestTemplate restTemplate;

    public void send(String callback) {
        restTemplate.postForLocation(callback, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        log.info("Sent time to {}", callback);
    }
}
