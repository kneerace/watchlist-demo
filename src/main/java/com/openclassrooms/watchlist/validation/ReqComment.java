package com.openclassrooms.watchlist.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReqCommentValidator.class)

public @interface ReqComment {
    String message() default "At least 15 characters comment for movies with rating <=5";

    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
