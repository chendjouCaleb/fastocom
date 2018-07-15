package app.identity;

import app.entity.identity.Account;
import app.entity.identity.Details;
import app.repository.contract.IAccountRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RunWith(MockitoJUnitRunner.class)
class IdentityManagerImplTest {
    @Mock private IAccountRepository accountRepository;
    private IdentityManagerImpl identityManager;
    private List<Account> accounts = new ArrayList<>();
    private String email = "chendjou@mail.com";
    private String badEmail = "www@www.com";
    private String phone = "674159162";
    private String badPhone = "000000";

    private Account account = new Account();
    @BeforeEach
    public void setUp(){

        account.setId(1);
        Details details = new Details();
        details.setEmail(email);
        details.setPhone(phone);
        account.setDetails(details);


        MockitoAnnotations.initMocks(this);
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);

        Mockito.when(accountRepository.findByEmail(email)).thenReturn(account);
        Mockito.when(accountRepository.findByEmail(badEmail)).thenThrow(NoSuchElementException.class);

        Mockito.when(accountRepository.findByPhone(phone)).thenReturn(account);
        Mockito.when(accountRepository.findByPhone(badPhone)).thenThrow(NoSuchElementException.class);


        identityManager = new IdentityManagerImpl(accountRepository);
    }
    @Test
    void save_With_Existing_Account_With_Email() {
        IdentityResult result = identityManager.save(account);
        System.out.println("Result: " + result);
        assertTrue(result.isFailed());
        assertEquals("email", result.getIdentityErrors().get(0).getCode());
    }

    @Test
    void save_With_Existing_Account_With_Phone() {
        Account account1 = new Account();
        account1.getDetails().setEmail(badEmail);
        account1.getDetails().setPhone(account.getDetails().getPhone());
        IdentityResult result = identityManager.save(account1);
        System.out.println("Result: " + result);
        assertTrue(result.isFailed());
        assertEquals("phone", result.getIdentityErrors().get(0).getCode());
    }


    @Test
    void findAccountById() {
    }

    @Test
    void findAccountByEmail() {
        Account account1 = identityManager.findAccountByEmail(email);
        assertEquals(account.getId(), account1.getId());
    }

    @Test
    void findAccountByEmail_with_Wrong_email() {
        assertThrows(NoSuchElementException.class, () -> identityManager.findAccountByEmail(badEmail));
    }

    @Test
    void findAccountByPhone() {
        Account account1 = identityManager.findAccountByPhone(phone);
        assertEquals(account.getId(), account1.getId());
    }

    @Test
    void findAccountByPhone_with_Wrong_Phone() {
        assertThrows(NoSuchElementException.class, () -> identityManager.findAccountByPhone(badPhone));
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void updateAccount() {
    }

}