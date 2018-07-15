package app.controller;

import app.entity.identity.Account;
import app.entity.identity.AccountRole;
import app.identity.IdentityManager;
import app.identity.IdentityResult;
import app.model.RegistrationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthController {
    private IdentityManager identityManager;
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(IdentityManager identityManager, PasswordEncoder passwordEncoder) {
        this.identityManager = identityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("login")
    public ModelAndView Login() {
        return new ModelAndView("account/login");
    }

    @GetMapping("registration")
    public ModelAndView Registration() {
        return new ModelAndView("account/registration")
                .addObject("registrationModel", new RegistrationModel());
    }

    @PostMapping("registration")
    public ModelAndView Registration(@ModelAttribute @Valid RegistrationModel model, BindingResult result) {
        if (!result.hasErrors()) {
            Account account = new Account();
            account.getDetails().setEmail(model.getEmail());
            account.getDetails().setPhone(model.getPhone());
            account.getUser().setName(model.getName());
            account.getUser().setSurname(model.getSurname());
            account.getDetails().setPasswordHash(passwordEncoder.encode(model.getPassword()));

            IdentityResult result1 = identityManager.save(account);

            if (result1.isSucceeded()) {
                return new ModelAndView("redirect:/login");
            }
            result1.getIdentityErrors().forEach(error -> {
                logger.info("Error: {}, {}", error.getCode(), error.getDescription());
                result.rejectValue(error.getCode(), "error.registrationModel", error.getDescription());

            });
        }
        return new ModelAndView("account/registration");
    }
}
