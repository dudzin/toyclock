package com.daro.toyclock.errors;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

// We could have a map here of fields and errors, so we can easily map them on UI. Skipping this for now
public class Errors {
    @Getter
    private List<String> errors = new LinkedList<>();

    public void addError(String error) {
        errors.add(error);
    }
}
