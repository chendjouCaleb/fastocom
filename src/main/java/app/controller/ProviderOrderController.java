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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("provider/{providerId}/order")
public class ProviderOrderController {
    private IPharmacyRepository pharmacyRepository;
    private IProviderRepository providerRepository;
    private IOrderRepository orderRepository;
    private IStockRepository stockRepository;


    public ProviderOrderController(IPharmacyRepository pharmacyRep, IProviderRepository providerRep, IOrderRepository orderRep, IStockRepository stockRep) {
        this.pharmacyRepository = pharmacyRep;
        this.providerRepository = providerRep;
        this.orderRepository = orderRep;
        this.stockRepository = stockRep;
    }

    @GetMapping()
    public ModelAndView List(@PathVariable("providerId") Integer id){
        Provider provider = providerRepository.findById(id);
        List<Order> orders = new ArrayList<>();
        provider.getStocks().forEach(stock -> orders.addAll(stock.getOrders()));
        return new ModelAndView("provider/order-list").addObject("provider", provider)
                .addObject("orders", orders);
    }

    @GetMapping("/add")
    public ModelAndView Add(@PathVariable("providerId") Integer id, @RequestParam("stock_Id") Integer stockId, Authentication authentication, Model model){
        Account account = ((AccountUserDetails)authentication.getPrincipal()).getAccount();
        List<Pharmacy> pharmacies = pharmacyRepository.findAll().stream()
                .filter(pharmacy -> pharmacy.getAdmin().getAccount().getId().equals(account.getId()))
                .collect(Collectors.toList());
        Stock stock = stockRepository.findById(stockId);
        Provider provider = providerRepository.findById(id);
        OrderModel orderModel = new OrderModel();
        orderModel.setStockId(stockId);

        model.addAttribute(stock).addAttribute(account).addAttribute(provider)
                .addAttribute("pharmacies", pharmacies);

        return new ModelAndView("provider/order-add").addObject("orderModel", orderModel);
    }

    @PostMapping("/add")
    public ModelAndView Add(@PathVariable("providerId") Integer id,@ModelAttribute @Valid OrderModel orderModel, BindingResult result,Authentication authentication) {
        Stock stock = stockRepository.findById(orderModel.getStockId());
        Provider provider = providerRepository.findById(id);
        Account account = ((AccountUserDetails)authentication.getPrincipal()).getAccount();
        List<Pharmacy> pharmacies = pharmacyRepository.findAll().stream()
                .filter(pharmacy -> pharmacy.getAdmin().getAccount().getId().equals(account.getId()))
                .collect(Collectors.toList());
        if(result.hasErrors()){
            return new ModelAndView("provider/order-add").addObject(stock).addObject(provider)
                    .addObject("pharmacies",pharmacies);
        }
        Order order = new Order();
        Pharmacy pharmacy = pharmacyRepository.findById(orderModel.getPharmacyId());


        order.setPharmacy(pharmacy);
        order.setStock(stock);
        order.setQuantity(orderModel.getQuantity());
        orderRepository.save(order);
        return new ModelAndView("redirect:/provider/" + stock.getOrganisation().getId() + "/stock");
    }
}
