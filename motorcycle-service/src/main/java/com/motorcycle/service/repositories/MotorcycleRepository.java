package com.motorcycle.service.repositories;

import com.motorcycle.service.entities.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotorcycleRepository extends JpaRepository<Motorcycle,Integer> {

    List<Motorcycle> findByUserId(Integer id);
}
