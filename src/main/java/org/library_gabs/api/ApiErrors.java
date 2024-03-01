package org.library_gabs.api;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Data
public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors (String messagemErro){
        this.errors = Arrays.asList(messagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}