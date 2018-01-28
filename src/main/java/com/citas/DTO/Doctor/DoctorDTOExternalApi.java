package com.citas.DTO.Doctor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTOExternalApi {
	
	private String id,name;
	private float price;
	
	public DoctorDTOExternalApi(String id, Float price, String name){
		this.id = id;
		this.price = price;
		this.name = name;
	}
	
	public DoctorDTOExternalApi(){}
}
