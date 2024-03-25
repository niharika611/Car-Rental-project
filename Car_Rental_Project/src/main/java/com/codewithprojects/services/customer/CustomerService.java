package com.codewithprojects.services.customer;

import java.util.List;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.dto.CarDto;
import com.codewithprojects.dto.CarDtoListDto;
import com.codewithprojects.dto.SearchCarDto;

public interface CustomerService {
	
	List<CarDto> getAllCars();
	
	boolean bookCar(BookCarDto bookCarDto);
	
	CarDto getCarById(Long id);
	
	List<BookCarDto> getBookingsByUserId(Long userId);
	
	CarDtoListDto searchCar(SearchCarDto searchCarDto);
}
