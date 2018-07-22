package app.controller;

import app.entity.identity.Account;
import app.entity.organisation.Organisation;
import app.entity.organisation.Pharmacy;
import app.model.OrganisationModel;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IAccountRepository;
import app.repository.contract.IOrganisationRepository;
import app.service.FileService;
import app.service.ServiceConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataConfiguration.class, ServiceConfiguration.class})
class OrganisationControllerTest {
    @Autowired private IAccountRepository accountRepository;
    @Autowired private IOrganisationRepository organisationRepository;
    @Autowired private FileService fileService;
    private OrganisationController organisationController;

    @BeforeEach
    public void setUp(){
        organisationController = new OrganisationController(organisationRepository, accountRepository, fileService);
    }


    @Test
    void add() {
        Account account = new Account();
        accountRepository.save(account);

        OrganisationModel model = new OrganisationModel();
        model.setType("pharmacy");
        model.setAdminAccountId(account.getId());
        model.setEmail("organisation@email.com");
        model.setPhone("748596321");
        model.setName("Organisation name");

        ModelAndView result = organisationController.Add(model, new BeanPropertyBindingResult(model, "model"));
        Organisation organisation = (Organisation) result.getModelMap().get("organisation");
        assertNotNull(organisation.getId());
        assertEquals(organisation.getClass(), Pharmacy.class);
        assertEquals(organisation.getInfo().getName(), model.getName());
        assertEquals(organisation.getContact().getEmail(), model.getEmail());
        assertEquals(organisation.getContact().getPhone(), model.getPhone());
        assertEquals(organisation.getAdmin().getAccount().getId(), model.getAdminAccountId());
        System.out.println("view name: " + result.getViewName());


    }

}