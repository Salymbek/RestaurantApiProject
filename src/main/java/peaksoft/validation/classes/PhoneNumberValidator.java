package peaksoft.validation.classes;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.validation.annotations.PhoneNumberValid;

public class PhoneNumberValidator implements ConstraintValidator <PhoneNumberValid,String> {
    @Override
    public boolean isValid(String phoneNumber,
                           ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber.length() == 13;
    }
}