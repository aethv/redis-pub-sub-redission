package com.example.spring_redis_subscriber.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {

    private final ProductService productService;

    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        productService.updateStock(message);
    }


}
