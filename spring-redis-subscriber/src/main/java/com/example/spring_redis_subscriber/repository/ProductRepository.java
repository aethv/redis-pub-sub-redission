package com.example.spring_redis_subscriber.repository;

import java.util.Optional;

import com.example.spring_redis_subscriber.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);
}
