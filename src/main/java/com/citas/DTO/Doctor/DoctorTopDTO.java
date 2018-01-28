package com.citas.DTO.Doctor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorTopDTO {
	
	private Integer id;
	private String name,surname, externalApiId;
	private Long totalPatients;
	
	public DoctorTopDTO(Integer id, String name, String surname, String externalApiId, Long totalPatients) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.externalApiId = externalApiId;
		this.totalPatients = totalPatients;
	}
	
	public DoctorTopDTO() {}
}
