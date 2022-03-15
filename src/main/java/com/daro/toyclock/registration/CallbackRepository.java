package com.daro.toyclock.registration;

import com.daro.toyclock.errors.CallbackException;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CallbackRepository {

    private ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();

    public void save(String callback, String interval) {
        if (store.containsKey(callback)) {
            throw new CallbackException("callback is already registered");
        }
        store.put(callback, interval);
    }
}
