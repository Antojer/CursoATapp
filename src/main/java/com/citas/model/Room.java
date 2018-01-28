package com.citas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Room implements Serializable{
	
	private static final long serialVersionUID = -3649398150043646380L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true)
	private Integer number;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="room")
	private List<MedicalVisit>  medicalVisits = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Clinic clinic;
	
	public String toString(){return id.toString();}
}
