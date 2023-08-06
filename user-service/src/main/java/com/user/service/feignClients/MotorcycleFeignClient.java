package com.user.service.feignClients;

import com.user.service.models.Motorcycle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//con feign client
@FeignClient(name = "motorcycle-service", url = "http://localhost:8083/")
@RequestMapping("api/motorcycle")
public interface MotorcycleFeignClient {

    @PostMapping
    Motorcycle save(@RequestBody Motorcycle motorcycle);

    @GetMapping("/user/{userId}")
    List<Motorcycle> getMotorcycles(@PathVariable Integer userId);
}
