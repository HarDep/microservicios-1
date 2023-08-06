package com.motorcycle.service.controllers;

import com.motorcycle.service.entities.Motorcycle;
import com.motorcycle.service.services.MotorcycleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/motorcycle")
public class MotorcycleController {

    @Autowired
    private MotorcycleService motorcycleService;

    @GetMapping
    public ResponseEntity<List<Motorcycle>> getMotorcycles(){
        List<Motorcycle> motorcycles = motorcycleService.getAll();
        return motorcycles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(motorcycles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycle(@PathVariable Integer id){
        return motorcycleService.getmotorcycleById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Motorcycle> saveMotorcycle(@Valid @RequestBody Motorcycle motorcycle){
        Motorcycle motorcycle1 = motorcycleService.save(motorcycle);
        return ResponseEntity.ok(motorcycle1);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable Integer userId){
        List<Motorcycle> motorcycles = motorcycleService.getAllByUserId(userId);
        return motorcycles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(motorcycles);
    }
}
