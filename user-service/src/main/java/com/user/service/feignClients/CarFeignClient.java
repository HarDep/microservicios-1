package com.user.service.feignClients;

import com.user.service.models.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//con feign client
@FeignClient(name = "car-service") //, url = "http://localhost:8082/"
@RequestMapping("api/car")
public interface CarFeignClient {

    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/user/{userId}")
    List<Car> getCars(@PathVariable Integer userId);
}
