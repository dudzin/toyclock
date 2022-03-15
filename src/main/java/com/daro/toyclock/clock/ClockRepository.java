package com.daro.toyclock.clock;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ClockRepository {

    private ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public void save(String callback, String interval) {
        if (store.containsKey(callback)) {
            throw new ClockException("callback is already registered");
        }
        store.put(callback, interval);
    }
}
