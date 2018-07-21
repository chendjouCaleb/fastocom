package app.controller;

import app.entity.market.Order;
import app.entity.market.Product;
import app.entity.market.Stock;
import app.entity.organisation.Pharmacy;
import app.entity.organisation.Provider;
import app.enumeration.OrderState;
import app.exception.ConstraintException;
import app.model.OrderModel;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = DataConfiguration.class)
class OrderApiControllerTest {

   @Autowired private IOrderRepository orderRepository;
   @Autowired private IPharmacyRepository pharmacyRepository;
   @Autowired private IStockRepository stockRepository;
   @Autowired private IProviderRepository providerRepository;
   @Autowired private IProductRepository productRepository;
    private OrderApiController controller;
    private Pharmacy pharmacy = new Pharmacy();
    private Provider provider = new Provider();
    private Stock stock = new Stock();
    private Product product = new Product();
    private Order order = new Order();
    @BeforeEach
    void setUp() {
        pharmacyRepository.save(pharmacy);
        providerRepository.save(provider);
        product.setProvider(provider);
        productRepository.save(product);

        stock.setOrganisation(provider);
        stock.setProduct(product);
        stock.setQuantity(20);
        stockRepository.save(stock);

        order.setQuantity(10);
        order.setStock(stock);
        order.setPharmacy(pharmacy);
        orderRepository.save(order);

        controller = new OrderApiController(orderRepository, pharmacyRepository, stockRepository);
    }

    @Test
    void add() {
        OrderModel orderModel = new OrderModel();
        orderModel.setPharmacyId(pharmacy.getId());
        orderModel.setStockId(stock.getId());
        orderModel.setQuantity(12);

        Order order = controller.Add(orderModel).getBody();
        assertNotNull(order);
        assertNotNull(order.getId());
        assertEquals(OrderState.WAITING, order.getState());
        assertEquals(order.getPharmacy().getId(), pharmacy.getId());
        assertEquals(order.getStock().getId(), stock.getId());
        assertEquals(orderModel.getQuantity(), order.getQuantity());

    }

    @Test
    public void accept_With_Pharmacy_Stock(){
        Stock pharmacyStock = new Stock();
        pharmacyStock.setProduct(product);
        pharmacyStock.setQuantity(10);
        pharmacyStock.setOrganisation(pharmacy);
        stockRepository.save(pharmacyStock);
        Order result = controller.Accept(order.getId()).getBody();

        assertNotNull(result);
        assertNotNull(result.getResponseDate());
        assertEquals(OrderState.ACCEPTED, result.getState());
        assertEquals(stock.getQuantity() - order.getQuantity(), result.getStock().getQuantity().intValue());
        assertEquals(order.getQuantity() + pharmacyStock.getQuantity(), stockRepository.findById(pharmacyStock.getId()).getQuantity().intValue());
    }

    @Test
    public void accept_With_non_Existing_Stock(){
        Order result = controller.Accept(order.getId()).getBody();

        assertNotNull(result);
        assertNotNull(result.getResponseDate());
        assertEquals(OrderState.ACCEPTED, result.getState());
        assertEquals(stock.getQuantity() - order.getQuantity(), result.getStock().getQuantity().intValue());
        Stock pharmacyStock = stockRepository.findByProductAndOrganisation(order.getStock().getProduct(), pharmacy);
        assertEquals(order.getQuantity(), pharmacyStock.getQuantity());
    }
    @Test
    public void accept_With_Unsufficient_Quantity(){
        stock.setQuantity(0);
        stockRepository.update(stock);
        assertThrows(ConstraintException.class, () -> controller.Accept(order.getId()).getBody());
    }

    @Test
    void reject(){
        controller.Reject(order.getId());
        Order result = orderRepository.findById(order.getId());
        assertEquals(OrderState.REJECTED, result.getState());
        assertNotNull(result.getResponseDate());
    }

    @Test
    void cancel(){
        controller.Cancel(order.getId());
        Order result = orderRepository.findById(order.getId());
        assertEquals(OrderState.CANCELED, result.getState());
    }
}