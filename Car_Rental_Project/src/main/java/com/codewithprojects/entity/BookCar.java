package com.codewithprojects.entity;


import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.codewithprojects.dto.BookCarDto;
import com.codewithprojects.enums.BookCarStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class BookCar {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date fromdate;
	
	private Date toDate;
	
	private Long days;
	
	private Long price;
	
	private BookCarStatus bookCarStatus;
	
	@ManyToOne(fetch= FetchType.LAZY, optional=false)
	@JoinColumn(name="user_id",nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne(fetch= FetchType.LAZY, optional=false)
	@JoinColumn(name="car_id",nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	private Car car;
	
	public BookCarDto getBookCarDto() {
		BookCarDto bookCarDto = new BookCarDto();
		bookCarDto.setId(id);
		bookCarDto.setDays(days);
		bookCarDto.setBookCarStatus(bookCarStatus);
		bookCarDto.setPrice(price);
		bookCarDto.setToDate(toDate);
		bookCarDto.setFromDate(fromdate);
		bookCarDto.setEmail(user.getEmail());
		bookCarDto.setUsername(user.getName());
		bookCarDto.setUserId(user.getId());
		bookCarDto.setCarId(car.getId());
		return bookCarDto;
	}
}
