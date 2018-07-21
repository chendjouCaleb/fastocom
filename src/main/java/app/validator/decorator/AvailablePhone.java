package app.validator.decorator;


import app.validator.implementation.AvailablePhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = AvailablePhoneValidator.class)
@Documented
public @interface AvailablePhone {
    String message() default "Cet numéro de téléphone est déjà utilisé";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
