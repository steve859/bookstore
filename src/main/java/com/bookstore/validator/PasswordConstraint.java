// package com.bookstore.validator;

// import java.lang.annotation.Target;

// import jakarta.validation.Constraint;
// import jakarta.validation.Payload;

// import static java.lang.annotation.ElementType.*;
// import static java.lang.annotation.RetentionPolicy.RUNTIME;

// import java.lang.annotation.Retention;
// @Target({ FIELD })
// @Retention(RUNTIME)
// @Constraint(validatedBy = {PasswordValidator.class})
// public @interface PasswordConstraint {
//     String message() default "Invalid date of birth";
//     int min();
//     Class<?>[] groups() default { };
//     Class<? extends Payload>[] payload() default { };
// }
