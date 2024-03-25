package com.codewithprojects.dto;

import java.sql.Blob;
import java.util.Date;

import lombok.Data;

@Data
public class CarDto {
	
	private Long id;

	private String brand;
	private String color;
	private String name;
	private String type;
	private String transmission;
	private String description;
	private Long price;
	private Date year;
	
	private byte[] image;
}
