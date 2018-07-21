package app.repository.contract;


import app.entity.organisation.Contact;
import app.exception.ElementAlreadyUsed;

public interface IContactRepository extends IRepository<Contact, Integer>{
    Contact findByEmail(String email);
    Contact findByContactEmailOrContactPhone(String ql);
    Contact findByPhone(String phone);

    Boolean phoneIsUsed(String phone);
    Boolean emailIsUsed(String email);
    Boolean phoneOrEmailIsUsed(String ql);

}
