package app.controller;

import app.entity.identity.Account;
import app.entity.organisation.Organisation;
import app.entity.organisation.Pharmacy;
import app.identity.AccountUserDetails;
import app.repository.contract.IOrganisationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api")
public class OrganisationApiController {
    private IOrganisationRepository organisationRepository;

    public OrganisationApiController(IOrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    @GetMapping("pharmacy")
    public ResponseEntity<List<Organisation>> Pharmacy(Authentication authentication){
        Account account = ((AccountUserDetails)authentication.getPrincipal()).getAccount();
        List<Organisation> pharmacies = organisationRepository.findAll().stream().filter(organisation -> {
            return organisation.getClass().equals(Pharmacy.class) && organisation.getAdmin().getAccount().getId().equals(account.getId());
        }).collect(Collectors.toList());
        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }
}
