package com.daro.toyclock.scheduler;

import com.daro.toyclock.clock.ClockRepository;
import com.daro.toyclock.sender.WebhookSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClockScheduler {

    private final ClockRepository clockRepository;
    private final WebhookSender sender;

    @Scheduled(fixedRate = 1000)
    public void execute() {
        var callbacks = clockRepository.copy();
        var timeNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        callbacks.forEach((callback, clock) -> {
            var startedInMili = clock.getStartedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if ((timeNow - startedInMili) % clock.getInterval() * 1000 == 0) {
                log.info("Time is up , send to {}", callback);
                sender.send(callback);
            }
        });

    }
}
