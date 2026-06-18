package com.example.mini_devops.service;

import com.example.mini_devops.entity.Product;
import com.example.mini_devops.repository.ProductRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.prometheus.metrics.core.metrics.Gauge;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final MeterRegistry meterRegistry;


    @Timed("product.creation")
    @CacheEvict(value = "products", allEntries = true)
    public Product create(Product product) {

        meterRegistry.counter("products.created").increment();
        return repository.save(product);
    }

    @Cacheable("products")
    public List<Product> findAll() {

        System.out.println("DB CALL - FIND ALL");

        return Timer.builder("products.findall")
                .register(meterRegistry)
                .record(() -> repository.findAll());
    }

    @Timed("products.findById")
    @Cacheable(value = "product", key = "#id")
    public Product findById(Long id) {

        System.out.println("DB CALL - FIND BY ID");

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    @Timed("products.update")
    @CachePut(value = "product", key = "#result.id")
    @CacheEvict(value = "products", allEntries = true)
    public Product update(Long id, Product product) {

        Product existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        existing.setName(product.getName());
        existing.setPrice(product.getPrice());

        return repository.save(existing);
    }

    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true)
    })
    public void delete(Long id) {

        Product existing = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        repository.delete(existing);
    }
}