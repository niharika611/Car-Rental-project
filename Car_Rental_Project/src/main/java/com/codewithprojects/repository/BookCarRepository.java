package com.codewithprojects.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.entity.BookCar;

@Repository
public interface BookCarRepository extends JpaRepository<BookCar, Long>{

	List<BookCar> findAllByUserId(Long userId);

}
