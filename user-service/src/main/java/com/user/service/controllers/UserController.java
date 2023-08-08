package com.user.service.controllers;

import com.user.service.entities.User;
import com.user.service.models.Car;
import com.user.service.models.Motorcycle;
import com.user.service.services.UserService;
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

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable Integer userId){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/motorcycles/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable Integer userId){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        List<Motorcycle> motorcycles = userService.getMotorcycles(userId);
        return ResponseEntity.ok(motorcycles);
    }

    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable Integer userId, @RequestBody Car car){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        Car car1 = userService.saveCar(userId,car);
        return ResponseEntity.ok(car1);
    }

    @PostMapping("/motorcycle/{userId}")
    public ResponseEntity<Motorcycle> saveMotorcycle(@PathVariable Integer userId, @RequestBody Motorcycle motorcycle){
        User user = userService.getUserById(userId).orElse(null);
        if (user == null)
            return ResponseEntity.notFound().build();
        Motorcycle motorcycle1 = userService.saveMotorcycle(userId,motorcycle);
        return ResponseEntity.ok(motorcycle1);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<?> getVehicles(@PathVariable Integer id){
        Map<String,Object> all = userService.getUserAndVehicles(id);
        if (all == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(all);
    }
}
