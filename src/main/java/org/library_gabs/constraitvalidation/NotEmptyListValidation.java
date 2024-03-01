package org.library_gabs.constraitvalidation;

import org.library_gabs.validation.NotEmptyList;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyListValidation
        implements ConstraintValidator<NotEmptyList, List> {
        @Override
        public void initialize(NotEmptyList constraintAnnotation) {
    }

        @Override
        public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }
}
