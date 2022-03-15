package com.daro.toyclock.clock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Clock {
    private final LocalDate startedAt;
    private Integer interval;
}
