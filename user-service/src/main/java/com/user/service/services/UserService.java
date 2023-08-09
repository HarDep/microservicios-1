package com.user.service.services;

import com.user.service.entities.User;
import com.user.service.feignClients.CarFeignClient;
import com.user.service.feignClients.MotorcycleFeignClient;
import com.user.service.models.Car;
import com.user.service.models.Motorcycle;
import com.user.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private MotorcycleFeignClient motorcycleFeignClient;

    //comunicación con rest Template
    public List<Car> getCars(Integer userId){
        List<Car> cars = restTemplate.getForObject(String.format("http://car-service/api/car/user/%s",userId)
                , List.class);
        return cars;
    }

    public List<Motorcycle> getMotorcycles(Integer userId){
        List<Motorcycle> motorcycles = restTemplate.getForObject(
                String.format("http://motorcycle-service/api/motorcycle/user/%s",userId)
                , List.class);
        return motorcycles;
    }

    //feign client

    public Car saveCar(Integer userId, Car car){
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Motorcycle saveMotorcycle(Integer userId, Motorcycle motorcycle){
        motorcycle.setUserId(userId);
        return motorcycleFeignClient.save(motorcycle);
    }

    public Map<String, Object> getUserAndVehicles(Integer id){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            return null;
        result.put("user",user);
        List<Car> cars = carFeignClient.getCars(id);
        result.put("cars",cars);
        List<Motorcycle> motorcycles = motorcycleFeignClient.getMotorcycles(id);
        result.put("motorcycles",motorcycles);
        return result;
    }

    //---

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
