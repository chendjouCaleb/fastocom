package app.repository.contract;


import app.entity.identity.Account;
import app.entity.organisation.Organisation;

public interface IOrganisationRepository extends IRepository<Organisation, Integer>{
    Boolean isAdmin(Organisation organisation, Account account);

}
