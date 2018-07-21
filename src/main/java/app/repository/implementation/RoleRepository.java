package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.identity.Role;
import app.repository.contract.IRoleRepository;
import org.hibernate.query.Query;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Qualifier
public class RoleRepository extends AbstractRepository<Role, Integer> implements IRoleRepository{


    public Role findByName(String name) {
        return (Role) em.createQuery("FROM Role r WHERE r.name=:name")
                .setParameter("name",name)
                .getSingleResult();
    }


    @Transactional
    public List<Account> listAccountByRole(String name) {
        return em.createQuery("SELECT a.account FROM AccountRole a WHERE a.role.name=:name")
                .setParameter("name", name)
                .getResultList();

    }


}
