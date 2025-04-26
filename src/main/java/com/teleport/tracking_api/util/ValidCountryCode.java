package com.teleport.tracking_api.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface ValidCountryCode {
    String message() default "Invalid country code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
