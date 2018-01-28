package com.citas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Appointment implements Serializable {

	private static final long serialVersionUID = -806591838791688536L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MedicalVisit medicalVisit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
}
