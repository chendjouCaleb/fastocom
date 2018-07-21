package app.controller;

import app.entity.market.Stock;
import app.entity.organisation.Provider;
import app.model.StockModel;
import app.repository.configuration.DataConfiguration;
import app.repository.contract.IProductRepository;
import app.repository.contract.IProviderRepository;
import app.repository.contract.IStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataConfiguration.class})
class ProviderStockControllerTest {
    @Autowired private IStockRepository stockRepository;
    @Autowired private IProductRepository productRepository;
    @Autowired private IProviderRepository providerRepository;
    private ProviderStockController controller;

    @BeforeEach
    void setUp() {
        controller = new ProviderStockController(stockRepository, productRepository, providerRepository);
    }

    @Test
    void add() {
        Provider provider =new Provider();
        provider.getInfo().setName("provider");
        provider.getInfo().setDescription("description for provider");
        providerRepository.save(provider);
        StockModel model = new StockModel();
        model.setName("Para");
        model.setQuantity(10);
        model.setRetailPrice(90.0);
        model.setWholeSalePrice(100.0);

        ModelAndView modelAndView = controller.Add(provider.getId(), model, new BeanPropertyBindingResult(model, "model"));
        Stock stock = (Stock) modelAndView.getModel().get("stock");

        assertEquals(model.getQuantity(), stock.getQuantity());
        assertEquals(model.getDescription(), stock.getProduct().getDescription());
        assertEquals(model.getName(), stock.getProduct().getName());
        assertEquals(model.getRetailPrice(), stock.getProduct().getRetailPrice());
        assertEquals(model.getWholeSalePrice(), stock.getProduct().getWholeSalePrice());

        assertEquals(provider.getId(), stock.getOrganisation().getId());
        assertEquals(provider.getId(), stock.getProduct().getProvider().getId());

    }


}