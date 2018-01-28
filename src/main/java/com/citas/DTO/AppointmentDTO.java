package com.citas.DTO;

import java.io.Serializable;

import com.citas.model.MedicalVisit;
import com.citas.model.Patient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDTO implements Serializable {

	private static final long serialVersionUID = 1120226019594020870L;

	private Integer id,patient, medicalVisit;
	
	public AppointmentDTO(Integer id,MedicalVisit medicalVisit, Patient patient) {
		this.id = id;
		this.medicalVisit = medicalVisit.getId();
		this.patient = patient.getId();
	}
	
	public AppointmentDTO() {}
	
}
