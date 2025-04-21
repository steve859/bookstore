// package com.bookstore.validator;

// import java.util.Objects;

// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;

// public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
//     private int min; 
//     @Override
//     public boolean isValid(String value, ConstraintValidatorContext context){
//         if(Objects.isNull(value)) return false;
//         return value.length() >= min;
//     }

//     @Override 
//     public void initialize(PasswordConstraint constraintsAnnotation){
//         ConstraintValidator.super.initialize(constraintsAnnotation);
//         min = constraintsAnnotation.min();
//     }
// }
