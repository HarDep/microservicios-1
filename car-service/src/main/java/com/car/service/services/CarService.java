package com.car.service.services;

import com.car.service.entities.Car;
import com.car.service.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Integer id){
        return carRepository.findById(id);
    }

    public List<Car> getAllByUserId(Integer userId){
        return carRepository.findByUserId(userId);
    }

    public Car save(Car car){
        return carRepository.save(car);
    }
}
