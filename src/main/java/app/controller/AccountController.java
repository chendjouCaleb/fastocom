package app.controller;

import app.entity.identity.Account;
import app.identity.AccountUserDetails;
import app.repository.contract.IAccountRepository;
import app.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;

@RequestMapping("account")
@Controller
public class AccountController {
    private static final String ACCOUNT_IMAGE_FOLDER = "E:/Everest-Lab/fastocom/file/account/image/";
    private Logger logger = LoggerFactory.getLogger(AccountController.class);
    private IAccountRepository accountRepository;
    private FileService fileService;

    public IAccountRepository getAccountRepository() {
        return accountRepository;
    }

    public AccountController(IAccountRepository accountRepository, FileService fileService) {
        this.accountRepository = accountRepository;
        this.fileService = fileService;
    }

    @GetMapping("home")
    public ModelAndView Home(Principal principal, Authentication authentication){
        AccountUserDetails userDetails =
                (AccountUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ModelAndView("account/home")
                .addObject("account", userDetails.getAccount());
    }

    @GetMapping("{id}/image")
    public ResponseEntity<InputStreamResource> DownloadImage(@PathVariable("id") Integer id) throws FileNotFoundException {
        Account account = accountRepository.findById(id);

        File file;
        if (account.getImage() != null){
            file = new File(ACCOUNT_IMAGE_FOLDER + account.getImage());
        }else {
            file = new File(ACCOUNT_IMAGE_FOLDER + "default.png");
        }
        logger.info("File: {}", file.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String contentType = "image/" + file.getName().split("\\.")[1];
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @PostMapping("{id}/image")
    public ModelAndView uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
        Account account = accountRepository.findById(id);

        System.out.println("Content-type: "+ file.getContentType());
        System.out.println("Type: " + file.getContentType().split("/")[1]);
        fileService.checkIfFileIs(file, "image");

        String name = account.getId().toString() + "." + file.getContentType().split("/")[1];
        String uri = ACCOUNT_IMAGE_FOLDER + name;
        logger.info("URI : {}", uri);
        fileService.saveUploadFile(file, uri);
        account.setImage(name);
        accountRepository.update(account);
        return new ModelAndView("account/home")
                    .addObject("account", account);
    }
}
