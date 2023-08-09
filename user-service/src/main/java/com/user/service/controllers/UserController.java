package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.models.Car;
import com.user.service.models.Motorcycle;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
        User user1 = userService.save(user);
        return ResponseEntity.ok(user1);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable Integer userId){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "motorcyclesCB", fallbackMethod = "fallBackGetMotorcycles")
    @GetMapping("/motorcycles/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable Integer userId){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Motorcycle> motorcycles = userService.getMotorcycles(userId);
        return ResponseEntity.ok(motorcycles);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        Car car1 = userService.saveCar(userId,car);
        return ResponseEntity.ok(car1);
    }

    @CircuitBreaker(name = "motorcyclesCB", fallbackMethod = "fallBackSaveMotorcycle")
    @PostMapping("/motorcycle/{userId}")
    public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable Integer userId, @RequestBody Motorcycle motorcycle){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        Motorcycle motorcycle1 = userService.saveMotorcycle(userId,motorcycle);
        return ResponseEntity.ok(motorcycle1);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/all/{id}")
    public ResponseEntity<?> getVehicles(@PathVariable Integer id){
        Map<String,Object> all = userService.getUserAndVehicles(id);
        if (all == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(all);
    }

    private ResponseEntity<?> fallBackGetCars(@PathVariable Integer userId,RuntimeException e){
        return ResponseEntity.ok(String.format("No se ha podido encontrar los carros del usuario con id %s, " +
                "mensaje de excepción: %s",userId,e.getMessage()));
    }

    private ResponseEntity<?> fallBackSaveCar(@PathVariable Integer userId, @RequestBody Car car,RuntimeException e){
        return ResponseEntity.ok(String.format("No se ha podido guardar el carro para el usuario con id %s, " +
                "mensaje de excepción: %s",userId,e.getMessage()));
    }
    private ResponseEntity<?> fallBackGetMotorcycles(@PathVariable Integer userId,RuntimeException e){
        return ResponseEntity.ok(String.format("No se ha podido encontrar las motos del usuario con id %s, " +
                "mensaje de excepción: %s",userId,e.getMessage()));
    }

    private ResponseEntity<?> fallBackSaveMotorcycle(@PathVariable Integer userId, @RequestBody Motorcycle motorcycle,RuntimeException e){
        return ResponseEntity.ok(String.format("No se ha podido guardar la moto para el usuario con id %s, " +
                "mensaje de excepción: %s",userId,e.getMessage()));
    }

    private ResponseEntity<?> fallBackGetAll(@PathVariable Integer id,RuntimeException e){
        return ResponseEntity.ok(String.format("No se ha podido encontrar los vehículos del usuario con id %s, " +
                "mensaje de excepción: %s",id,e.getMessage()));
    }
}
