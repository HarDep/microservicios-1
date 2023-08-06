package com.user.service.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Motorcycle {
    private String brand;

    private String model;

    private Integer userId;
}
