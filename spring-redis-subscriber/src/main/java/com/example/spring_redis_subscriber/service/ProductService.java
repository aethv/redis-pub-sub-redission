package com.example.spring_redis_subscriber.service;

import com.example.spring_redis_subscriber.model.ProductRequest;
import com.example.spring_redis_subscriber.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final RedissonClient redisson;
    private final ProductRepository productRepository;
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${app.name}")
    private String appName;

    @Retryable(value = org.redisson.client.RedisTimeoutException.class, maxAttempts = 2, backoff = @Backoff(delay = 2000))
    public void updateStock(final Message message) {
        RLock lock = redisson.getLock("update-stock-lock");
        try {

            lock.lock(4, TimeUnit.SECONDS);

            log.info("RedissonClient Lock By : Subscriber %s".formatted(appName));
            String body = new String(message.getBody(), StandardCharsets.UTF_8);

            ProductRequest requestProduct = null;
            try {
                requestProduct = mapper.readValue(body, ProductRequest.class);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
            }

            productRepository.findByProductName(requestProduct.getProductName())
                    .ifPresent(product -> {
                        product.setStock(product.getStock() > 1 ? product.getStock() - 1 : 0);
                        product.setLastUpdateBy("SUBSCRIBER %s".formatted(appName));
                        productRepository.save(product);
                    });
        } finally {
            if (lock != null && lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("lock released by %s".formatted(appName));
            }
        }
    }

}
