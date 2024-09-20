package com.learning.productservice.service;

import com.learning.productservice.dto.ProductDto;
import com.learning.productservice.repository.ProductRepository;
import com.learning.productservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductDto> getAll() {
        return productRepository.findAll()
                .map(EntityDtoUtil::toProductDto);
    }

    public Flux<ProductDto> getProductByPriceRange(int min, int max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toProductDto);
    }

    public Mono<ProductDto> getById(String id) {
        return productRepository.findById(id)
                .map(EntityDtoUtil::toProductDto);
    }

    public Mono<ProductDto> create(Mono<ProductDto> productDto) {
        return productDto
                .map(EntityDtoUtil::toProduct)
                .flatMap(productRepository::insert)
                .map(EntityDtoUtil::toProductDto);
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(p -> productDtoMono
                        .map(EntityDtoUtil::toProduct)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(productRepository::save)
                .map(EntityDtoUtil::toProductDto);
    }

    public Mono<Void> delete(String id) {
        return productRepository.deleteById(id);
    }
}
