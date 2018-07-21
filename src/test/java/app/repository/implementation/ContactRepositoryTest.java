package app.repository.implementation;

import app.entity.identity.Account;
import app.entity.organisation.Contact;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IAccountRepository;
import app.repository.contract.IContactRepository;
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
public class ContactRepositoryTest {
    @Autowired private IContactRepository contactRepository;
    @Autowired private IAccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(ContactRepositoryTest.class);
    private Contact contact = new Contact();
    @BeforeEach
    void setUp() {
        contact.setEmail("adress@email.com");
        contact.setPhone("123456789");
        assertNull(contact.getId());
        contactRepository.save(contact);
    }

    @Test
    void saveContactTest() {


        assertNotNull(contact.getId());
    }

    @Test
    void updateContactTest() {
        contact = contactRepository.findById(contact.getId());
        contact.setEmail("email@yahoo.fr");
        contactRepository.update(contact);
        contact = contactRepository.findById(contact.getId());
        assertEquals(contact.getEmail(),"email@yahoo.fr");
    }

    @Test
    void findContactByTest(){
        logger.info("new contact: {}",contact.getId());
        Integer id = contact.getId();
        assertNotNull(contactRepository.findById(id));
    }

    @Test
    void listContactTest(){
        assertTrue(contactRepository.findAll().size() >= 1);
    }

    @Test
    void findContactByPhone(){
        String phone = contact.getPhone();

        Contact newcontact = contactRepository.findByPhone(phone);
        assertEquals(newcontact.getPhone(), phone);
    }

    @Test
    void findContactByEmail(){
        String email = contact.getEmail();

        Contact newcontact = contactRepository.findByEmail(email);
        assertEquals(newcontact.getEmail(), email);
    }

    @Test
    void findContactByEmailWithWrongEmail(){
        assertThrows(NoSuchElementException.class, () -> {contactRepository.findByEmail("wrong@email.com") ;});
    }

    @Test
    void findContactByPhoneWithWrongPhone(){
        assertThrows(NoSuchElementException.class, () -> contactRepository.findByPhone("wrongphone"));
    }


    @Test
    void deleteContactTest(){
        logger.info("new contact: {}",contact.getId());
        Integer id = contact.getId();
        contactRepository.delete(contact);
        assertThrows(NoSuchElementException.class, () -> contactRepository.findById(id));
    }

    @Test
    void findcontactByEmailOrPhoneOnlyWithEmail(){
        String email = contact.getEmail();

        Contact newcontact = contactRepository.findByContactEmailOrContactPhone(email);
        assertEquals(newcontact.getEmail(), email);
    }

    @Test
    void findContactByEmailOrPhoneWithPhone(){
        String phone = contact.getPhone();

        Contact newcontact = contactRepository.findByContactEmailOrContactPhone(phone);
        assertEquals(newcontact.getPhone(), phone);
    }

    @Test
    void findContactByEmailOrPhone_WithWrongEmail_OR_Phone(){
        assertThrows(NoSuchElementException.class, () ->
            contactRepository.findByContactEmailOrContactPhone("feg"));
    }

    @Test
    void checkIfPhoneIsUsed(){
        String phone = contact.getPhone();

        assertTrue(contactRepository.phoneIsUsed(phone));
    }

    @Test
    void checkIfEmailIsUsed(){
        String email = contact.getEmail();
        assertTrue(contactRepository.emailIsUsed(email));
    }

    @Test
    void checkIfEmailOrPhoneIsUsed(){
        String email = contact.getEmail();
        String phone = contact.getPhone();
        assertTrue(contactRepository.emailIsUsed(email));
        assertTrue(contactRepository.phoneIsUsed(phone));
    }

    @Test
    void checkIfPhoneIsUsedWithNotUsedPhone(){
        assertFalse(contactRepository.phoneIsUsed("123965845632563"));
    }

    @Test
    void checkIfEmailIsUsedWithNotUsedEmail(){
        assertFalse(contactRepository.emailIsUsed("myemail@email.com"));
    }

    @Test
    void checkIfEmailOrPhoneIsUsed_With_Not_Used_PhoneAndEmail(){
        assertFalse(contactRepository.phoneOrEmailIsUsed("myemail@email.com"));
        assertFalse(contactRepository.phoneOrEmailIsUsed("897556312356"));
    }

    @Test
    void checkEmailUsedByAccount(){
        Account account = new Account();
        account.getDetails().setEmail("account@email.com");
        accountRepository.save(account);
        assertTrue(contactRepository.emailIsUsed("account@email.com"));
    }

    @Test
    void checkPhoneUsedByAccount(){
        Account account = new Account();
        account.getDetails().setPhone("748596748596");
        accountRepository.save(account);
        assertTrue(contactRepository.phoneIsUsed("748596748596"));
    }

    @AfterEach
    void tearDown() {
        contactRepository.deleteAll();
    }
}
