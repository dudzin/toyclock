package com.daro.toyclock.clock;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Clock {
    private final LocalDateTime startedAt;
    private Integer interval;
}
