package app.controller;

import app.entity.identity.Account;
import app.entity.market.Order;
import app.entity.market.Stock;
import app.entity.organisation.Pharmacy;
import app.entity.organisation.Provider;
import app.identity.AccountUserDetails;
import app.model.OrderModel;
import app.repository.contract.IOrderRepository;
import app.repository.contract.IPharmacyRepository;
import app.repository.contract.IProviderRepository;
import app.repository.contract.IStockRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pharmacy/{pharmacyId}/order")
public class PharmacyOrderController {
    private IPharmacyRepository pharmacyRepository;
    private IProviderRepository providerRepository;
    private IOrderRepository orderRepository;
    private IStockRepository stockRepository;


    public PharmacyOrderController(IPharmacyRepository pharmacyRep, IProviderRepository providerRep, IOrderRepository orderRep, IStockRepository stockRep) {
        this.pharmacyRepository = pharmacyRep;
        this.providerRepository = providerRep;
        this.orderRepository = orderRep;
        this.stockRepository = stockRep;
    }

    @GetMapping()
    public ModelAndView List(@PathVariable("pharmacyId") Integer id){
        Pharmacy pharmacy = pharmacyRepository.findById(id);

        return new ModelAndView("pharmacy/order-list").addObject("pharmacy", pharmacy)
                .addObject("orders", pharmacy.getOrders());
    }


}
