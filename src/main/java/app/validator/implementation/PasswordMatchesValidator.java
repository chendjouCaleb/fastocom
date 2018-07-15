package app.validator.implementation;

import app.model.RegistrationModel;
import app.validator.decorator.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    public void initialize(PasswordMatches constraintAnnotation) {
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context){
        RegistrationModel model = (RegistrationModel) obj;
        return model.getPassword().equals(model.getPasswordMatcher());
    }
}
