package com.daro.toyclock.clock;

import com.daro.toyclock.sender.WebhookSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ClockRepository {

    private final ConcurrentHashMap<String, Clock> store;
    private final WebhookSender webhookSender;

    public ClockRepository(WebhookSender webhookSender) {
        this.webhookSender = webhookSender;
        store = new ConcurrentHashMap<>();
    }

    public void save(String callback, Integer interval) {
        if (store.containsKey(callback)) {
            throw new ClockException("callback is already registered");
        }
        var clock = new Clock(LocalDateTime.now(), interval);
        store.put(callback, clock);
        log.info("New callback created for {}, with interval of {}s", callback, interval);
        webhookSender.send(callback);
    }


    public void update(String callback, Integer interval) {
        if (!store.containsKey(callback)) {
            throw new ClockException("callback unknown");
        }
        var clock = store.get(callback);
        clock.setInterval(interval);
        store.put(callback, clock);
        log.info("Callback updated for {}, with new interval of {}s", callback, interval);
    }

    public void delete(String callback) {
        if (!store.containsKey(callback)) {
            throw new ClockException("callback unknown");
        }
        store.remove(callback);
        log.info("Callback deleted for {}", callback);
    }


    public void clean() {
        store.clear();
    }

    public int count() {
        return store.size();
    }

    public HashMap<String, Clock> copy() {
        return new HashMap<>(store);
    }
}
