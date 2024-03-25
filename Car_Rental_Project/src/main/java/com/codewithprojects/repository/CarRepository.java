package com.codewithprojects.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithprojects.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

}
