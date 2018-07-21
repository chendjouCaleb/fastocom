package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.organisation.Provider;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IAccountRepository;
import app.repository.contract.IOrganisationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = { DataConfiguration.class})
class OrganisationRepositoryTest {
    @Autowired private IAccountRepository accountRepository;
    @Autowired private IOrganisationRepository organisationRepository;
    private Account account = new Account();
    private Provider provider = new Provider();

    @BeforeEach
    void setUp() {
        provider.getInfo().setName("xxx");
        provider.getInfo().setDescription("xxx111111111111111111111111111");
        account = accountRepository.save(account);
        provider.getAdmin().setAccount(account);
        provider = (Provider) organisationRepository.save(provider);
        System.out.println("Provider ID: " + provider.getId());
    }

    @Test
    void isAdmin() {
        assertTrue(organisationRepository
                .isAdmin(provider, account));
    }

}