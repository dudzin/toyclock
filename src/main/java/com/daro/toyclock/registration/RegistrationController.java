package com.daro.toyclock.registration;

import com.daro.toyclock.errors.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class RegistrationController {

    @PostMapping("/register")
    public Map test(@RequestBody @Valid RegistrationRequest registrationRequest) throws IllegalAccessException {
        return Map.of();
    }


    // usually we would have a global error handler, but leaving this one here for now

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Errors handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        var errors = new Errors();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.addError(errorMessage);

        });
        return errors;
    }


}
