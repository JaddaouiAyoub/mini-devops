package com.example.mini_devops.service;

import com.example.mini_devops.entity.Product;
import com.example.mini_devops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product create(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {

        System.out.println("DB CALL");

        return repository.findById(id)
                .orElseThrow();
    }

    @CachePut(value = "products", key = "#id")
    public Product update(Long id, Product request) {

        Product product = repository.findById(id)
                .orElseThrow();

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        return repository.save(product);
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {

        repository.deleteById(id);
    }
}