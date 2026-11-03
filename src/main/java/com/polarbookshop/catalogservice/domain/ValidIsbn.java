package com.polarbookshop.catalogservice.domain;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, RECORD_COMPONENT})
@Retention(RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
public @interface ValidIsbn {
    String message() default "Invalid ISBN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}