package com.car.service.repositories;

import com.car.service.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {

    List<Car> findByUserId(Integer id);
}
