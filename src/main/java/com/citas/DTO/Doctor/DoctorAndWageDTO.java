package com.citas.DTO.Doctor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAndWageDTO implements Serializable{

	private static final long serialVersionUID = -2399991210544504771L;

	private Integer id;
	private String name,surname;
	private float wage;
	
	public DoctorAndWageDTO(Integer id, String name, String surname, float wage) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.wage = wage;
	}
	
	public DoctorAndWageDTO() {}
}
