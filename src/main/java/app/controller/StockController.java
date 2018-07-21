package app.controller;

import app.entity.market.Product;
import app.entity.organisation.Organisation;
import app.repository.contract.IProductRepository;
import app.repository.contract.IStockRepository;
import app.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("stock")
public class StockController {
private static String STOCK_IMAGE_FOLDER = "E:/Everest-Lab/fastocom/file/stock/images/";

    private IStockRepository stockRepository;
    private IProductRepository productRepository;
    private FileService fileService;

    public StockController(IStockRepository stockRepository, IProductRepository productRepository, FileService fileService) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    @GetMapping("{id}/image")
    public ResponseEntity<InputStreamResource> DownloadImage(@PathVariable("id")Integer id) throws FileNotFoundException {
        Product product = stockRepository.findById(id).getProduct();

        File file;
        if (product.getImage() != null){
            file = new File(STOCK_IMAGE_FOLDER + product.getImage());
        }else {
            file = new File(STOCK_IMAGE_FOLDER + "default.jpeg");
        }
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        String contentType = "image/" + file.getName().split("\\.")[1];
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName())
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }

    @PostMapping("{id}/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable("id") Integer id) throws IOException {
        Product product = stockRepository.findById(id).getProduct();

        fileService.checkIfFileIs(file, "image");

        String name = product.getId().toString() + "." + file.getContentType().split("/")[1];
        String uri = STOCK_IMAGE_FOLDER + name;
        fileService.saveUploadFile(file, uri);
        product.setImage(name);
        productRepository.update(product);
        return new ResponseEntity<Object>("Successfully uploaded - " +
                file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }
}
