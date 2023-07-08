package com.study.reactive.controller;

import com.study.reactive.dto.ProductDto;
import com.study.reactive.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping ("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping ("/all")
    public Flux<ProductDto> getProducts() {
        return productService.getProducts();
    }

    @GetMapping ("/{id}")
    public Mono<ProductDto> getProductById (@PathVariable String id) {
        return productService.getProductById(id);
    }

    @GetMapping ("/product-range") //Example =====> `products/product-range?min=10&max=50`
    public Flux<ProductDto> getOrderByRange (@RequestParam double min,
                                             @RequestParam double max) {
        return productService.getOrderByRange(min, max);
    }

    @PostMapping ("/save")
    public Mono<ProductDto> saveProduct (@RequestBody Mono<ProductDto> productDto) {
        return productService.saveProduct(productDto);
    }

    @PutMapping ("/update/{id}")
    public Mono<ProductDto> updateProduct (@PathVariable String id,
                                           @RequestBody Mono<ProductDto> productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping ("/delete/{id}")
    public Mono<Void> deleteProduct (@PathVariable String id) {
        return productService.deleteProduct(id);
    }

}
