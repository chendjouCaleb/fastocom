package app.validator.implementation;



import app.repository.contract.IContactRepository;
import app.validator.decorator.AvailableEmail;
import app.validator.decorator.ValidEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvailableEmailValidator implements ConstraintValidator<AvailableEmail, String>{
    private IContactRepository contactRepository;

    @Autowired public AvailableEmailValidator(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        System.out.println("contact ID: " + contactRepository.toString());
        return !contactRepository.emailIsUsed(value);
    }
}
