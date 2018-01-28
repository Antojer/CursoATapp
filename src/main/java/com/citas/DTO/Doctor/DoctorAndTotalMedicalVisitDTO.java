package com.citas.DTO.Doctor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAndTotalMedicalVisitDTO {
	
	private Integer id;
	private Long medicalVisitTotal;
	private String name, surname, externalApiId;
	
	public DoctorAndTotalMedicalVisitDTO(Integer id, Long medicalVisitTotal, String name, String surname,
			String externalApiId) {
		this.id = id;
		this.medicalVisitTotal = medicalVisitTotal;
		this.name = name;
		this.surname = surname;
		this.externalApiId = externalApiId;
	}
	
	public DoctorAndTotalMedicalVisitDTO() {}
}
