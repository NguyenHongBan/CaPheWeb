package com.api.cafe.services;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CafeValidation {
    private static Validator validator;

    public static <T> CafeResponse validateObject(T object, BindingResult bindingResult) {
        if (validator != null) {
            validator.validate(object, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            List<ErrorDetail> errorDetails = new ArrayList<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                ErrorDetail errorDetail = new ErrorDetail(error.getField(), error.getDefaultMessage());
                errorDetails.add(errorDetail);
            }

            String errorMessage = errorDetails.stream()
                    .map(errorDetail -> String.format("%s", errorDetail.getMessage()))
                    .collect(Collectors.joining("; "));

            return CafeResponse.builder()
                    .message(errorMessage)
                    .data(errorDetails)
                    .build();
        } else {
            return CafeResponse.builder()
                    .message("Validation successful.")
                    .data(object)
                    .build();
        }
    }
}
