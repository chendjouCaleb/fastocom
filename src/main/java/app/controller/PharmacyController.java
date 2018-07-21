package app.controller;

import app.entity.organisation.Pharmacy;
import app.repository.contract.IPharmacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("pharmacy")
public class PharmacyController {
    private IPharmacyRepository pharmacyRepository;
    private Logger logger = LoggerFactory.getLogger(PharmacyController.class);

    public PharmacyController(IPharmacyRepository pharmacyRepository) {
        this.pharmacyRepository = pharmacyRepository;
    }

    @GetMapping()
    public ModelAndView List(){
        return new ModelAndView("pharmacy/list")
                .addObject("pharmacies", pharmacyRepository.findAll());
    }

    @GetMapping("/{id}/home")
    public ModelAndView Home(@PathVariable("id") Integer id){
        Pharmacy pharmacy = pharmacyRepository.findById(id);
        logger.info("Pharmacy {}", pharmacy);
        return new ModelAndView("pharmacy/home")
                .addObject("pharmacy", pharmacy);

    }
}
