package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.organisation.Organisation;
import app.repository.contract.IOrganisationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier
public class OrganisationRepository extends AbstractRepository<Organisation, Integer> implements IOrganisationRepository {

    @Transactional
    public Boolean isAdmin(Organisation organisation, Account account) {
        return em.createQuery("FROM Organisation o WHERE o.admin.account.id=:id")
                .setParameter("id", account.getId())
                .getResultList().size() > 0;
    }
}
