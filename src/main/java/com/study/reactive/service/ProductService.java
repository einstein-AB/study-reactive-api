package com.study.reactive.service;

import com.study.reactive.dto.ProductDto;
import com.study.reactive.entity.Product;
import com.study.reactive.repository.ProductRepository;
import com.study.reactive.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> getProducts() {
        Flux<ProductDto> products = productRepository.findAll()
                .map(product -> ProductUtil.entityToDto(product));
        return products;
    }

    public Mono<ProductDto> getProductById (String id) {
        Mono<ProductDto> prod = productRepository.findById(id)
                .map(product -> ProductUtil.entityToDto(product));
        return prod;
    }

    public Flux<ProductDto> getOrderByRange (double min, double max) {
        Flux<ProductDto> productsByRange = productRepository.findByPriceBetween(Range.closed(min, max))
                .map(product -> ProductUtil.entityToDto(product));
        return productsByRange;
    }

    public Mono<ProductDto> saveProduct (Mono<ProductDto> productDto) {
        Mono<ProductDto> savedProduct = productDto.map(productDto1 -> ProductUtil.dtoToEntity(productDto1))
                .flatMap(product -> productRepository.save(product))
                .map(productDto1 -> ProductUtil.entityToDto(productDto1));
        return savedProduct;
    }

    public Mono<ProductDto> updateProduct (String id, Mono<ProductDto> productDto) {
        Mono<ProductDto> updatedProduct = productRepository.findById(id)
                .flatMap(product -> productDto.map(productDto1 -> ProductUtil.dtoToEntity(productDto1)))
                .doOnNext(product -> product.setId(id))
                .flatMap(product -> productRepository.save(product))
                .map(product -> ProductUtil.entityToDto(product));
        return updatedProduct;
    }

    public Mono<Void> deleteProduct (String id) {
        return productRepository.deleteById(id);
    }
}
