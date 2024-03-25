package com.codewithprojects.services.customer;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
import com.codewithprojects.entity.User;
import com.codewithprojects.enums.BookCarStatus;
import com.codewithprojects.repository.BookCarRepository;
import com.codewithprojects.repository.CarRepository;
import com.codewithprojects.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CarRepository carRepository;
	
	private final UserRepository userRepository;
	
	private final BookCarRepository bookCarRepository;

	@Override
	public List<CarDto> getAllCars() {
		return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
	}

	@Override
	public boolean bookCar(BookCarDto bookCarDto) {
		Optional<Car> optionalCar=carRepository.findById(bookCarDto.getCarId());
		Optional<User> optionalUser=userRepository.findById(bookCarDto.getUserId());
		if(optionalCar.isPresent() && optionalUser.isPresent()) {
			Car existingCar=optionalCar.get();
			BookCar bookCar=new BookCar();
			bookCar.setUser(optionalUser.get());
			bookCar.setCar(existingCar);
			bookCar.setBookCarStatus(BookCarStatus.PENDING);
			bookCar.setFromdate(bookCarDto.getFromDate());
			bookCar.setToDate(bookCarDto.getToDate());
			long diffInMilliSeconds=bookCarDto.getToDate().getTime()-bookCarDto.getFromDate().getTime();
			long days=TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
			bookCar.setDays(days);
			bookCar.setPrice(existingCar.getPrice()*days);
			bookCarRepository.save(bookCar);
			return true;
		}
		return false;
	}

	@Override
	public CarDto getCarById(Long id) {
		Optional<Car> optionalCar=carRepository.findById(id);
		return optionalCar.map(Car::getCarDto).orElse(null);
	}

	@Override
	public List<BookCarDto> getBookingsByUserId(Long userId) {
		return bookCarRepository.findAllByUserId(userId).stream().map(BookCar::getBookCarDto).collect(Collectors.toList());
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
