package org.library_gabs.validation;

import org.library_gabs.constraitvalidation.NotEmptyListValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidation.class)
public @interface NotEmptyList {
    String message() default "The loan can not be empty.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
