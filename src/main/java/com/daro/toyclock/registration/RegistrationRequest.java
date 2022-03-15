package com.daro.toyclock.registration;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class RegistrationRequest {
    @NotBlank(message = "callback is mandatory")
    @Getter
    private String callback;
    @NotBlank(message = "interval is mandatory")
    @Getter
    private String interval;
}
