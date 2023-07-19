package com.bikkadit.electronic.store.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Constraint(validatedBy = {ImageNameValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented


public @interface ImageNameValid {

    String message() default "Invalid Image Name !!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

