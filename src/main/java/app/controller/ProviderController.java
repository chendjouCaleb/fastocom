package app.controller;

import app.entity.organisation.Provider;
import app.repository.contract.IProviderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("provider")
public class ProviderController {
    private IProviderRepository providerRepository;

    public ProviderController(IProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
    
    @GetMapping()
    public ModelAndView List(){
        return new ModelAndView("provider/list")
                .addObject("providers", providerRepository.findAll());
    }

    @GetMapping("/{id}/home")
    public ModelAndView Home(@PathVariable("id") Integer id){
        Provider provider = providerRepository.findById(id);
        System.out.println("Provider: " + provider.getId());
        return new ModelAndView("provider/home")
                .addObject("provider", provider);
                
    }
}
