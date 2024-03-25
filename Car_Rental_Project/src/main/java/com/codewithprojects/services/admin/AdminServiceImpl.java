package com.codewithprojects.services.admin;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.dto.CarDto;
import com.codewithprojects.dto.CarDtoListDto;
import com.codewithprojects.dto.SearchCarDto;
import com.codewithprojects.entity.BookCar;
import com.codewithprojects.entity.Car;
import com.codewithprojects.enums.BookCarStatus;
import com.codewithprojects.repository.BookCarRepository;
import com.codewithprojects.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
	
	private final CarRepository carRepository;
	
	private final BookCarRepository bookCarRepository;

	@Override
	public boolean postCar(CarDto carDto) throws IOException {
		try {
		Car car=new Car();
		car.setName(carDto.getName());
		car.setBrand(carDto.getBrand());
		car.setColor(carDto.getColor());
		car.setPrice(carDto.getPrice());
		car.setYear(carDto.getYear());
		car.setType(carDto.getType());
		car.setDescription(carDto.getDescription());
		car.setTransmission(carDto.getTransmission());
		car.setImage(carDto.getImage());
		carRepository.save(car);
		return true;
		}catch(Exception e) {
			return false;
		}
	}

	@Override
	public List<CarDto> getAllCars() {
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public void deleteCar(Long id) {
		carRepository.deleteById(id);
	}

	@Override
	public CarDto getCarById(Long id) {
		Optional<Car> optionalCar=carRepository.findById(id);
		return optionalCar.map(Car :: getCarDto).orElse(null);
	}

	@Override
	public boolean updateCar(Long id, CarDto carDto) {
		Optional<Car> optionalCar=carRepository.findById(id);
		if(optionalCar.isPresent()) {
			Car existingCar=optionalCar.get();
			if(carDto.getImage()!=null)
				existingCar.setImage(carDto.getImage());
			existingCar.setPrice(carDto.getPrice());
			existingCar.setYear(carDto.getYear());
			existingCar.setType(carDto.getType());
			existingCar.setDescription(carDto.getDescription());
			existingCar.setTransmission(carDto.getTransmission());
			existingCar.setColor(carDto.getColor());
			existingCar.setName(carDto.getName());
			existingCar.setBrand(carDto.getBrand());
			carRepository.save(existingCar);
			return true;
		}
		else
		return false;
	}

	@Override
	public List<BookCarDto> getBookings() {
		return bookCarRepository.findAll().stream().map(BookCar::getBookCarDto).collect(Collectors.toList());
	}

	@Override
	public boolean changeBookingStatus(Long id, String status) {
		Optional<BookCar> optionalBookcar=bookCarRepository.findById(id);
		if(optionalBookcar.isPresent()) {
			BookCar existingBookcar=optionalBookcar.get();
			System.out.println(status);
			if(Objects.equals(status, "Approve"))
				existingBookcar.setBookCarStatus(BookCarStatus.APPROVED);
			else if((Objects.equals(status, "Reject")))
				existingBookcar.setBookCarStatus(BookCarStatus.REJECTED);
			bookCarRepository.save(existingBookcar);
			return true;
		}
		return false;
	}

	@Override
	public CarDtoListDto searchCar(SearchCarDto searchCarDto) {
		Car car=new Car();
		car.setBrand(searchCarDto.getBrand());
		car.setType(searchCarDto.getType());
		car.setTransmission(searchCarDto.getTransmission());
		car.setColor(searchCarDto.getColor());
		ExampleMatcher exampleMatcher=ExampleMatcher.matchingAll()
				.withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> carExample=Example.of(car,exampleMatcher);
		List<Car> carList=carRepository.findAll(carExample);
		CarDtoListDto carDtoListDto=new CarDtoListDto();
		carDtoListDto.setCarDtoList(carList.stream().map(Car::getCarDto).collect(Collectors.toList()));
		return carDtoListDto;
	}

}
