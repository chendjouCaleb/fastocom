package app.controller;

import app.entity.market.Order;
import app.entity.organisation.Pharmacy;
import app.repository.contract.IOrderRepository;
import app.repository.contract.IPharmacyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("pharmacy/{pharmacyId}/order/")
public class OrderController {
    private IPharmacyRepository pharmacyRepository;
    private IOrderRepository orderRepository;

    public OrderController(IPharmacyRepository pharmacyRepository, IOrderRepository orderRepository) {
        this.pharmacyRepository = pharmacyRepository;
        this.orderRepository = orderRepository;
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
