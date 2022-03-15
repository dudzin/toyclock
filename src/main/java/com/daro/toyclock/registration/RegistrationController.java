package com.daro.toyclock.registration;

import com.daro.toyclock.errors.CallbackException;
import com.daro.toyclock.errors.Errors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final CallbackRepository callbackRepository;

    @PostMapping("/register")
    public void test(@RequestBody @Valid RegistrationRequest registrationRequest) throws IllegalAccessException {
        callbackRepository.save(registrationRequest.getCallback(), registrationRequest.getInterval());
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CallbackException.class)
    public Errors handleExceptions(
            CallbackException ex) {
        var errors = new Errors();
        errors.addError(ex.getMessage());
        return errors;
    }

}
