package com.daro.toyclock.clock;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DeRegistrationRequest {
    @NotBlank(message = "callback is mandatory")
    @Getter
    private String callback;
}
