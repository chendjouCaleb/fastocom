package app.validator.implementation;



import app.repository.contract.IContactRepository;
import app.validator.decorator.AvailableEmail;
import app.validator.decorator.AvailablePhone;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AvailablePhoneValidator implements ConstraintValidator<AvailablePhone, String>{
    private IContactRepository contactRepository;

    @Autowired public AvailablePhoneValidator(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !contactRepository.phoneIsUsed(value);
    }
}
