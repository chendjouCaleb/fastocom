package app.repository.implementation;

import app.entity.identity.Account;
import app.repository.contract.IAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.NoSuchElementException;

@Service
@Qualifier
public class AccountRepository extends AbstractRepository<Account, Integer> implements IAccountRepository {
    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        String ql = "FROM Account a WHERE a.details.email = :email";
        Query query = em.createQuery(ql).setParameter("email", email);

        try {
            return (Account) query.getSingleResult();
        }catch (NoResultException e) {
            throw new NoSuchElementException("Noting instance of Account with email '" + email +"' was found");
        }
    }

    @Transactional(readOnly = true)
    public Account findByPhone(String phone) {
        String ql = "FROM Account a WHERE a.details.phone = :phone";
        Query query = em.createQuery(ql).setParameter("phone", phone);
        try {
            return (Account) query.getSingleResult();
        }catch (NoResultException e) {
            throw new NoSuchElementException("Noting instance of Account with phone '" + phone +"' was found");
        }
    }

    public Account findByContactEmailOrContactPhone(String ql) {
        try{
            return findByEmail(ql);
        }catch (NoSuchElementException e){
            return findByPhone(ql);
        }
    }
}
