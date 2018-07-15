package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.identity.Details;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IAccountRepository;
import org.h2.store.Data;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataConfiguration.class})
class AccountRepositoryTest {
    private Account account = new Account();
    @Autowired
    private IAccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(AccountRepositoryTest.class);
    @BeforeEach
    void setUp() {
        Account account = new Account();
        Details details = new Details();
        details.setEmail("chendjou@gmail.com");
        details.setPhone("6985471");
        account.setDetails(details);
    }

    @Test
    void saveAccountTest() {
        assertNull(account.getId());
        accountRepository.save(account);
        assertNotNull(account.getId());
    }

    @Test
    void updateAccountTest() {
        accountRepository.save(account);

        account = accountRepository.findById(account.getId());
        account.getDetails().setEmail("email@yahoo.fr");
        accountRepository.update(account);
        account = accountRepository.findById(account.getId());
        assertEquals(account.getDetails().getEmail(),"email@yahoo.fr");
    }

    @Test
    void findAccountByIdTest(){
        accountRepository.save(account);
        logger.info("new account: {}",account.getId());
        Integer id = account.getId();
        assertNotNull(accountRepository.findById(id));
    }

    @Test
    void listAccountTest(){
        accountRepository.save(account);
        assertTrue(accountRepository.findAll().size() == 1);
    }

    @Test
    void findAccountByPhone(){
        String phone = "74859632";
        account.getDetails().setPhone(phone);
        accountRepository.save(account);

        Account newAccount = accountRepository.findByPhone(phone);
        assertEquals(newAccount.getDetails().getPhone(), phone);
    }

    @Test
    void findAccountByEmail(){
        String email = "chendjou@email.com";
        account.getDetails().setEmail(email);
        accountRepository.save(account);

        Account newAccount = accountRepository.findByEmail(email);
        assertEquals(newAccount.getDetails().getEmail(), email);
    }

    @Test
    void findAccountByEmailWithWrongEmail(){
        accountRepository.save(account);
        assertThrows(NoSuchElementException.class, () -> accountRepository.findByEmail("wrong@email.com"));
    }

    @Test
    void findAccountByPhoneWithWrongPhone(){
        accountRepository.save(account);
        assertThrows(NoSuchElementException.class, () -> accountRepository.findByPhone("wrongphone"));
    }


    @Test
    void deleteAccountTest(){
        account = accountRepository.save(account);
        logger.info("new account: {}",account.getId());
        Integer id = account.getId();
        accountRepository.delete(account);
        assertThrows(NoSuchElementException.class, () -> {
            accountRepository.findById(id);
        });
    }

    @Test
    void findAccountByEmailOrPhone_With_Only_Email(){
        String email = "chendjou@email.com";
        account.getDetails().setEmail(email);
        accountRepository.save(account);


        Account newAccount = accountRepository.findByContactEmailOrContactPhone(email);
        assertEquals(newAccount.getDetails().getEmail(), email);
    }

    @Test
    void findAccountByEmailOrPhoneWithPhone(){
        String phone = "74859632";
        account.getDetails().setPhone(phone);
        accountRepository.save(account);


        Account newAccount = accountRepository.findByContactEmailOrContactPhone(phone);
        assertEquals(newAccount.getDetails().getPhone(), phone);
    }

    @Test
    void findAccountByEmailOrPhoneWithWrongEmailORPhone(){
        accountRepository.save(account);
        assertThrows(NoSuchElementException.class, () ->
                accountRepository.findByContactEmailOrContactPhone("feg"));
    }



    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
    }

}