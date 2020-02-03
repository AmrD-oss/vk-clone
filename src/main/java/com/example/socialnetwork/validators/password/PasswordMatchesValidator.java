package com.example.socialnetwork.validators.password;

import com.example.socialnetwork.models.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<ValidPassword, Object> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserEntity userEntity = (UserEntity) obj;
        return userEntity.getPassword().equals(userEntity.getMatchingPassword());
    }
}