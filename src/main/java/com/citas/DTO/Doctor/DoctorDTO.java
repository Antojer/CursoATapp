package com.citas.DTO.Doctor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO implements Serializable {

	private static final long serialVersionUID = -8762431912956066742L;
	
	private Integer id;
	private String name,surname, externalApiId;
	public DoctorDTO(Integer id, String name, String surname, String externalApiId) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.externalApiId = externalApiId;
	}
	
	public DoctorDTO() {}
}
