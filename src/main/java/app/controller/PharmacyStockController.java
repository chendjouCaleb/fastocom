package app.controller;

import app.entity.market.Product;
import app.entity.market.Stock;
import app.entity.organisation.Pharmacy;
import app.entity.organisation.Provider;
import app.model.StockModel;
import app.repository.contract.IPharmacyRepository;
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
@RequestMapping("pharmacy/{pharmacyId}/stock")
public class PharmacyStockController extends HttpController{
    private IStockRepository stockRepository;
    private IProductRepository productRepository;
    private IProviderRepository providerRepository;
    private IPharmacyRepository pharmacyRepository;

    public PharmacyStockController(IStockRepository stockRepository, IProductRepository productRepository, IProviderRepository providerRepository, IPharmacyRepository pharmacyRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.pharmacyRepository = pharmacyRepository;
    }


    @GetMapping
    public ModelAndView List(@PathVariable("pharmacyId") Integer id){
        Pharmacy pharmacy = pharmacyRepository.findById(id);
        return new ModelAndView("pharmacy/stock-list")
                .addObject("stocks", stockRepository.listByOrganisation(pharmacy))
                .addObject("pharmacy", pharmacy);
    }


    @GetMapping("{id}/quantity")
    public ResponseEntity<Stock> Home(@PathVariable("id") Integer id, @RequestParam("quantity") Integer quantity){
        Stock stock = stockRepository.findById(id);
        stock.setQuantity(quantity);
        stockRepository.update(stock);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
}
