package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.identity.AccountRole;
import app.entity.identity.Role;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IAccountRepository;
import app.repository.contract.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataConfiguration.class})
class RoleRepositoryTest {
    @Autowired private IRoleRepository roleRepository;
    @Autowired private IAccountRepository accountRepository;

    private Role role = new Role();
    private Account account = new Account();
    @BeforeEach
    void setUp() {
        role.setName("user");
        roleRepository.save(role);
        account.getAccountRoles().add(new AccountRole(account, role));
        accountRepository.save(account);
    }

    @Test
    void findByName() {
        assertEquals(role.getName(), roleRepository.findByName(role.getName()).getName());
    }

    @Test
    void listAccountByRole() {
        assertEquals(1, roleRepository.listAccountByRole(role.getName()).size());
    }

}