package com.example.spring_redis_subscriber.runner;

import com.example.spring_redis_subscriber.model.Product;
import com.example.spring_redis_subscriber.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InsertProduct implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        productRepository.deleteAll();
        productRepository.save(Product.builder().productName("lock-product").stock(20).lastUpdateBy(null).build());
    }
}
