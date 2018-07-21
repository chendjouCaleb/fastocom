package app.controller;

import app.entity.market.Product;
import app.entity.market.Stock;
import app.entity.organisation.Provider;
import app.model.StockModel;
import app.repository.contract.IProductRepository;
import app.repository.contract.IProviderRepository;
import app.repository.contract.IStockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("provider/{providerId}/stock")
public class ProviderStockController extends HttpController{
    private IStockRepository stockRepository;
    private IProductRepository productRepository;
    private IProviderRepository providerRepository;

    public ProviderStockController(IStockRepository stockRep, IProductRepository productRep, IProviderRepository providerRep) {
        this.stockRepository = stockRep;
        this.productRepository = productRep;
        this.providerRepository = providerRep;
    }

    @GetMapping(value = "{id}/home", name = "stock-home")
    public ModelAndView Home(@PathVariable("id") Integer id){
        Stock stock = stockRepository.findById(id);
        return new ModelAndView("provider/stock-home")
                .addObject("provider", stock.getOrganisation())
                .addObject("stock", stock);
    }

    @GetMapping
    public ModelAndView List(@PathVariable("providerId") Integer id){
        Provider provider = providerRepository.findById(id);
        return new ModelAndView("provider/stock-list")
                .addObject("stocks", stockRepository.listByOrganisation(provider))
                .addObject("provider", provider);
    }

    @GetMapping("/add")
    public ModelAndView Add(@PathVariable("providerId") Integer id){
        Provider provider = providerRepository.findById(id);
        return new ModelAndView("provider/stock-add")
                .addObject("stockModel", new StockModel())
                .addObject("provider", provider);
    }

    @PostMapping("/add")
    public ModelAndView Add(@PathVariable("providerId") Integer id,@ModelAttribute @Valid StockModel model, BindingResult result){
        Provider provider = providerRepository.findById(id);
        if(result.hasErrors()){
            return new ModelAndView("provider/stock-add").addObject("provider", provider);
        }

        Product product = new Product();
        product.setDescription(model.getDescription());
        product.setName(model.getName());
        product.setRetailPrice(model.getRetailPrice());
        product.setWholeSalePrice(model.getWholeSalePrice());

        Stock stock = new Stock();
        stock.setQuantity(model.getQuantity());

        product.setProvider(provider);
        stock.setProduct(product);
        stock.setOrganisation(provider);

        productRepository.save(product);
        stockRepository.save(stock);
        return new ModelAndView("redirect:/provider/"+ provider.getId() + "/stock/" + stock.getId()+"/home")
                .addObject("stock", stock);
    }

    @GetMapping("{id}/quantity")
    public ResponseEntity<Stock> Home(@PathVariable("id") Integer id, @RequestParam("quantity") Integer quantity){
        Stock stock = stockRepository.findById(id);
        stock.setQuantity(quantity);
        stockRepository.update(stock);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
}
