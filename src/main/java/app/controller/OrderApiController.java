package app.controller;

import app.entity.market.Order;
import app.entity.market.Stock;
import app.entity.organisation.Pharmacy;
import app.enumeration.OrderState;
import app.exception.ConstraintException;
import app.model.OrderModel;
import app.repository.contract.IOrderRepository;
import app.repository.contract.IPharmacyRepository;
import app.repository.contract.IStockRepository;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/order")
public class OrderApiController {
    private IOrderRepository orderRepository;
    private IPharmacyRepository pharmacyRepository;
    private IStockRepository stockRepository;

    public OrderApiController(IOrderRepository orderRepository, IPharmacyRepository pharmacyRepository, IStockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.pharmacyRepository = pharmacyRepository;
        this.stockRepository = stockRepository;
    }

    @PostMapping
    public ResponseEntity<Order> Add(@RequestBody @Valid OrderModel orderModel) {
        Order order = new Order();
        Pharmacy pharmacy = pharmacyRepository.findById(orderModel.getPharmacyId());
        Stock stock = stockRepository.findById(orderModel.getStockId());

        order.setPharmacy(pharmacy);
        order.setStock(stock);
        order.setQuantity(orderModel.getQuantity());
        orderRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping("{id}/accept")
    public ResponseEntity<Order> Accept(@PathVariable("id") Integer id){
        Order order = orderRepository.findById(id);

        Stock providerStock = order.getStock();
        providerStock.setQuantity(providerStock.getQuantity() - order.getQuantity());
        if(providerStock.getQuantity() < 0){
            throw new ConstraintException("Vous n'avez pas assez de quantité en stock pour répondre à cette commande");
        }

        Stock pharmacyStock;
        try {
            pharmacyStock = stockRepository.findByProductAndOrganisation(order.getStock().getProduct(), order.getPharmacy());
        }catch (NoSuchElementException ex){
            pharmacyStock = new Stock();
            pharmacyStock.setProduct(order.getStock().getProduct());
            pharmacyStock.setQuantity(0);
            pharmacyStock.setOrganisation(order.getPharmacy());
            stockRepository.save(pharmacyStock);
        }
        pharmacyStock.setQuantity(pharmacyStock.getQuantity() + order.getQuantity());

        order.setState(OrderState.ACCEPTED);
        order.setResponseDate(new DateTime());

        orderRepository.update(order);
        stockRepository.update(pharmacyStock);
        stockRepository.update(providerStock);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("{id}/reject")
    public ResponseEntity<?> Reject(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id);
        order.setResponseDate(new DateTime());
        order.setState(OrderState.REJECTED);
        orderRepository.update(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}/cancel")
    public ResponseEntity<?> Cancel(@PathVariable("id") Integer id) {
        Order order = orderRepository.findById(id);
        order.setState(OrderState.CANCELED);
        orderRepository.update(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
