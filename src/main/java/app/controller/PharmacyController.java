package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("pharmacy")
public class PharmacyController {

    @GetMapping("add")
    @ResponseBody
    public String Add(){
        return "addPharmacie";
    }
}
