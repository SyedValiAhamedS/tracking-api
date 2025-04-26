package com.teleport.tracking_api.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Locale;

public class CountryCodeValidator implements ConstraintValidator<ValidCountryCode, String> {
	public static final String[] isoLocaleCodes = Locale.getISOCountries();
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (Arrays.asList(isoLocaleCodes).contains(value)) {
            return true;
        }
        return false;
    }
}
