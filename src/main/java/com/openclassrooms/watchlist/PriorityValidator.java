package com.openclassrooms.watchlist;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<Priority, String> {
    // ConstraintValidator<A, T> where A is the annotation and T is the type of the value to validate

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.trim().length() == 1 && "LMH".contains(value.toUpperCase());
    } // end isValid
} // end PriorityValidator
