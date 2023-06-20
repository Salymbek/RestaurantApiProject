package peaksoft.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.validation.classes.PasswordValidator;

import java.lang.annotation.*;


@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordValid {

    String message() default "Invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}