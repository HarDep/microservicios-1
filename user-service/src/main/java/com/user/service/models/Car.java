package com.user.service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Car {
    private String brand;

    private String model;

    private Integer userId;
}
