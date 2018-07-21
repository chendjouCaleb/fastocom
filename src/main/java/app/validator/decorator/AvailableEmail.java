package app.validator.decorator;


import app.validator.implementation.AvailableEmailValidator;
import app.validator.implementation.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AvailableEmailValidator.class)
@Documented
public @interface AvailableEmail {
    String message() default "Cet adresse électronique est déjà utilisée";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
