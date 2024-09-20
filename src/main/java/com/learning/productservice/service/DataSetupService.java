package com.learning.productservice.service;

import com.learning.productservice.dto.ProductDto;
import com.learning.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DataSetupService implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        ProductDto product1 = new ProductDto("4k-tv", 1000);
        ProductDto product2 = new ProductDto("slr-camera", 7500);
        ProductDto product3 = new ProductDto("iphone", 800);
        ProductDto product4 = new ProductDto("headphone", 100);

        Flux.just(product1, product2, product3, product4)
                .flatMap(p -> productService.create(Mono.just(p)))
                .subscribe(
                        result -> System.out.println("Product saved: " + result),
                        error -> System.err.println("Error occurred: " + error),
                        () -> System.out.println("All products saved!")
                );
    }
}
