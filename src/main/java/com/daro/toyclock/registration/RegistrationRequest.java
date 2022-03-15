package com.daro.toyclock.registration;

import javax.validation.constraints.NotBlank;

public class RegistrationRequest {
    @NotBlank(message = "callback is mandatory")
    private String callback;
    @NotBlank(message = "interval is mandatory")
    private String interval;
}
