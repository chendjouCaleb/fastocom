package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("account")
public class AccountController {

    @GetMapping("home")
    public ModelAndView Home(){
        return new ModelAndView("account/home");
    };
}
