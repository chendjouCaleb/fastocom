package app.controller;

import app.entity.identity.Account;
import app.entity.market.Order;
import app.entity.market.Stock;
import app.entity.organisation.Pharmacy;
import app.identity.AccountUserDetails;
import app.model.OrderModel;
import app.repository.contract.IOrderRepository;
import app.repository.contract.IPharmacyRepository;
import app.repository.contract.IStockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("pharmacy/{pharmacyId}/order/")
public class OrderController {
    private IPharmacyRepository pharmacyRepository;
    private IOrderRepository orderRepository;
    private IStockRepository stockRepository;


    public OrderController(IPharmacyRepository pharmacyRepository, IOrderRepository orderRepository, IStockRepository stockRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @GetMapping()
    public ModelAndView List(@PathVariable("pharmacyId") Integer id){
        Pharmacy pharmacy = pharmacyRepository.findById(id);
        return new ModelAndView("pharmacy/order-list").addObject("pharmacy", pharmacy);
    }

    @GetMapping("{id}/display")
    public ModelAndView Display(@PathVariable("id")Integer orderId){
        Order order = orderRepository.findById(orderId);
        return new ModelAndView("pharmacy/order-display")
                .addObject("order", order)
                .addObject("pharmacy", order.getPharmacy());
    }
}
