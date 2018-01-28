package com.citas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Doctor implements Serializable{

	private static final long serialVersionUID = 5378732994395080694L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name, surname;
	
	@Column(unique = true)
	private String externalApiId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="doctor")
	List<MedicalVisit>  medicalvisits = new ArrayList<>();
	
	public String toString(){return id.toString();}
}
