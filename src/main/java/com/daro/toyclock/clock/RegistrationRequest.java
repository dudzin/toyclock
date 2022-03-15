package com.daro.toyclock.clock;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegistrationRequest {
    @NotBlank(message = "callback is mandatory")
    @Getter
    private String callback;

    @Getter
    @Min(5)
    @Max(4 * 60 * 60) //4 hours
    @NotNull(message= "interval is mandatory")
    private Integer interval;
}
