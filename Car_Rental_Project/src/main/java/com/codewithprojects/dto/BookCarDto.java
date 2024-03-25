package com.codewithprojects.dto;

import java.util.Date;

import com.codewithprojects.enums.BookCarStatus;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class BookCarDto {
	
	private Long id;
	
	private Date fromDate;
	
	private Date toDate;
	
	private Long days;
	
	private Long price;
	
	private BookCarStatus bookCarStatus;
	
	private Long carId;
	
	private Long userId;
	
	private String username;
	
	private String email;
	
}
