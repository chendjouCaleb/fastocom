package app.repository.implementation;

import app.entity.organisation.Contact;
import app.repository.contract.IContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.NoSuchElementException;

@Service
@Qualifier
@Transactional
public class ContactRepository extends AbstractRepository<Contact, Integer>implements IContactRepository {
    private Logger logger = LoggerFactory.getLogger(ContactRepository.class);

    @Transactional(readOnly = true)
    public Contact findByEmail(String email) {
        String ql = "FROM Contact c WHERE c.email = :email";
        Query query = em.createQuery(ql).setParameter("email", email);

        try {
            return (Contact) query.getSingleResult();
        }catch (NoResultException e) {
            throw new NoSuchElementException("Noting instance of account with email '" + email +"' was found");
        }
    }

    @Transactional(readOnly = true)
    public Contact findByPhone(String phone) {
        String ql = "FROM Contact c WHERE c.phone = :phone";
        Query query = em.createQuery(ql).setParameter("phone", phone);
        try {
            return (Contact) query.getSingleResult();
        }catch (NoResultException e) {
            throw new NoSuchElementException("Noting instance of Contact with phone '" + phone +"' was found");
        }
    }


    @Transactional
    public Contact findByContactEmailOrContactPhone(String ql) {
        try{
            return findByEmail(ql);
        }catch (NoSuchElementException e){
            return findByPhone(ql);
        }
    }


    @Transactional
    public Boolean phoneIsUsed(String phone) {
        try{
            findByPhone(phone);
            return true;
        }catch (NoSuchElementException ignore){
            return em.createQuery("FROM Details d WHERE d.phone=:phone")
                    .setParameter("phone", phone)
                    .getResultList().size()>0;

        }
    }


    public Boolean emailIsUsed(String email) {
        try {
            findByEmail(email);
            return true;
        }catch (NoSuchElementException ex){
            return em.createQuery("FROM Details d WHERE d.email=:email")
                    .setParameter("email", email).getResultList()
                    .size()>0;

        }
    }


    public Boolean phoneOrEmailIsUsed(String ql) {
        if(phoneIsUsed(ql)){
            return true;
        }
        return emailIsUsed(ql);
    }

}
