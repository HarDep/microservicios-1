package com.motorcycle.service.services;

import com.motorcycle.service.entities.Motorcycle;
import com.motorcycle.service.repositories.MotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorcycleService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    public List<Motorcycle> getAll(){
        return motorcycleRepository.findAll();
    }

    public Optional<Motorcycle> getmotorcycleById(Integer id){
        return motorcycleRepository.findById(id);
    }

    public List<Motorcycle> getAllByUserId(Integer userId){
        return motorcycleRepository.findByUserId(userId);
    }

    public Motorcycle save(Motorcycle motorcycle){
        return motorcycleRepository.save(motorcycle);
    }
}
