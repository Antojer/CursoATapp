package com.citas.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO implements Serializable{

	private static final long serialVersionUID = -8473613072169356762L;
	
	Integer id;
	
	private String name,surname;
	
	public PatientDTO(Integer id, String name, String surname) {
		this.id = id;
		this.name=name;
		this.surname=surname;
	}
	
	public PatientDTO() {}
}
