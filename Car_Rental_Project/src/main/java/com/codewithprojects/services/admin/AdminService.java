package com.codewithprojects.services.admin;

import java.io.IOException;
import java.util.List;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.dto.CarDto;
import com.codewithprojects.dto.CarDtoListDto;
import com.codewithprojects.dto.SearchCarDto;

public interface AdminService {
	
	boolean postCar(CarDto carDto) throws IOException;
	
	List<CarDto> getAllCars();
	
	void deleteCar(Long id);
	
	CarDto getCarById(Long id);
	
	boolean updateCar(Long id,CarDto carDto);
	
	List<BookCarDto> getBookings();
	
	boolean changeBookingStatus(Long id, String status);
	
	CarDtoListDto searchCar(SearchCarDto searchCarDto);
	
}
