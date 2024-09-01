package com.example.spring_redis_subscriber.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest implements Serializable {

    private String productName;
    private Integer stock;
    private String lastUpdateBy;

}
