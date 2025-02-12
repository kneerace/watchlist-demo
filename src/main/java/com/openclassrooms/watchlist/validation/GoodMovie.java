package com.openclassrooms.watchlist.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // the annotation can be applied to classes
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GoodMovieValidator.class)

public @interface GoodMovie {
    String message() default "if the movie rating is above 5, priority should be at least M i.e. Medium";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
