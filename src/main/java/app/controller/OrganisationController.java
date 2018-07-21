package app.controller;

import app.entity.identity.Account;
import app.entity.organisation.Organisation;
import app.entity.organisation.Pharmacy;
import app.entity.organisation.Provider;
import app.identity.AccountUserDetails;
import app.model.OrganisationModel;
import app.repository.contract.IAccountRepository;
import app.repository.contract.IOrganisationRepository;
import app.repository.implementation.OrganisationRepository;
import app.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("organisation")
public class OrganisationController {
    private static final String ORGANISATION_IMAGE_FOLDER = "E:/Everest-Lab/fastocom/file/organisation/images/";
    private IOrganisationRepository organisationRepository;
    private IAccountRepository accountRepository;
    private FileService fileService;

    public OrganisationController(IOrganisationRepository organisationRep, IAccountRepository accountRep, FileService fileService) {
        this.organisationRepository = organisationRep;
        this.accountRepository = accountRep;
        this.fileService = fileService;
    }

    @GetMapping("add")
    public ModelAndView Add(Authentication authentication, @RequestParam("orgType")String type){
        Account account = ((AccountUserDetails)authentication.getPrincipal()).getAccount();
        OrganisationModel model = new OrganisationModel();
        model.setAdminAccount(account);
        model.setAdminAccountId(account.getId());
        model.setType(type);
        if(type.equals("pharmacy")){
            model.setDisplayType("une pharmacie");
        }else if(type.equals("provider")){
            model.setDisplayType("un fournisseur");
        }

        return new ModelAndView("organisation/add")
                .addObject("organisationModel", model);
    }

    @PostMapping("add")
    public ModelAndView Add(@ModelAttribute @Valid OrganisationModel model, BindingResult result){
        System.out.println("result: " + result.getClass());
        Account account = accountRepository.findById(model.getAdminAccountId());
        model.setAdminAccount(account);
        if(!result.hasErrors()) {
            Organisation organisation = null;
            if (model.getType().equals("pharmacy")) {
                organisation = new Pharmacy();
            } else if (model.getType().equals("provider")) {
                organisation = new Provider();
            }
            Account admin = accountRepository.findById(model.getAdminAccountId());
            organisation.getAdmin().setAccount(admin);
            organisation.getContact().setPhone(model.getPhone());
            organisation.getContact().setEmail(model.getEmail());
            organisation.getInfo().setName(model.getName());
            organisation.getInfo().setDescription("Cet organisation n'a pas de description");

            organisationRepository.save(organisation);
            return new ModelAndView("redirect:/" + model.getType()+"/"+organisation.getId() + "/home").
                    addObject("organisation", organisation);
        }
        return new ModelAndView("organisation/add");
    }

    @GetMapping("{id}/image")
    public ResponseEntity<InputStreamResource> DownloadImage(@PathVariable("id")Integer id) throws FileNotFoundException {
        Organisation organisation = organisationRepository.findById(id);

        File file;
        if (organisation.getImage() != null){
            file = new File(ORGANISATION_IMAGE_FOLDER + organisation.getImage());
        }else {
            file = new File(ORGANISATION_IMAGE_FOLDER + "default.jpg");
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String contentType = "image/" + file.getName().split("\\.")[1];
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @PostMapping("{id}/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
        Organisation organisation = organisationRepository.findById(id);

        System.out.println("Content-type: "+ file.getContentType());
        System.out.println("Type: " + file.getContentType().split("/")[1]);
        fileService.checkIfFileIs(file, "image");

        String name = organisation.getId().toString() + "." + file.getContentType().split("/")[1];
        String uri = ORGANISATION_IMAGE_FOLDER + name;
        fileService.saveUploadFile(file, uri);
        organisation.setImage(name);
        organisationRepository.update(organisation);
        return new ResponseEntity<Object>("Successfully uploaded - " +
                file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
}
