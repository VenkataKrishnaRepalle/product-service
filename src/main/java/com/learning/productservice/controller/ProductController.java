package com.learning.productservice.controller;

import com.learning.productservice.dto.ProductDto;
import com.learning.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("all")
    public Flux<ProductDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable String id) {
        return productService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/price-range")
    public Flux<ProductDto> getProductByPriceRange(@RequestParam("min") int min,
                                                   @RequestParam("max") int max) {
        return productService.getProductByPriceRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> create(@RequestBody Mono<ProductDto> productDto) {
        return productService.create(productDto);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> update(@PathVariable String id, @RequestBody Mono<ProductDto> productDto) {
        return productService.update(id, productDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return productService.delete(id);
    }
}
