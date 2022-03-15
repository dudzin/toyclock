package com.daro.toyclock.clock;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class Clock {
    private final LocalDate startedAt;
    private final Integer interval;
}
