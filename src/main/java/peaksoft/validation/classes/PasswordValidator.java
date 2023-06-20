package peaksoft.validation.classes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.validation.annotations.PasswordValid;

public class PasswordValidator implements ConstraintValidator<PasswordValid,String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length()>4 ;
    }
}