package com.codewithprojects.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.dto.CarDto;
import com.codewithprojects.dto.SearchCarDto;
import com.codewithprojects.services.admin.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService adminService;
	
	@PostMapping("/car")
	public ResponseEntity<?> postCar(@RequestBody CarDto carDto) throws IOException{
		
		boolean success=adminService.postCar(carDto);
		if(success)
			return ResponseEntity.status(HttpStatus.CREATED).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@GetMapping("/cars")
	public ResponseEntity<?> getAllCars() {
		return ResponseEntity.ok(adminService.getAllCars());
	}
	
	@DeleteMapping("/car/{id}")
	public ResponseEntity<?> deleteCar(@PathVariable Long id) {
		adminService.deleteCar(id);
		return  ResponseEntity.ok(null);
	}
	
	@GetMapping("/car/{id}")
	public ResponseEntity<?> getcarById(@PathVariable Long id) {
		CarDto carDto= adminService.getCarById(id);
		return ResponseEntity.ok(carDto);
	}
	
	@PutMapping("/car/{id}")
	public ResponseEntity<?> updateCar(@PathVariable Long id,@RequestBody CarDto carDto) {
		try {
		boolean success=adminService.updateCar(id,carDto);
		if(success)
		return ResponseEntity.status(HttpStatus.OK).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping("/car/bookings")
	public ResponseEntity<List<BookCarDto>> getBookings() {
		return ResponseEntity.ok(adminService.getBookings());
	}
	
	@GetMapping("/car/booking/{id}/{status}")
	public ResponseEntity<?> changeBookingStatus(@PathVariable Long id,@PathVariable String status) {
		boolean success=adminService.changeBookingStatus(id, status);
		if(success)
			return ResponseEntity.status(HttpStatus.OK).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping("/car/search")
	public ResponseEntity<?> searchCar(@RequestBody SearchCarDto searchCarDto) {
		return ResponseEntity.ok(adminService.searchCar(searchCarDto));
	}
}
