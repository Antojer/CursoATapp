package com.citas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
public class Patient implements Serializable {
	
	private static final long serialVersionUID = -4617972754834851310L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name, surname;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="patient")
	private List<Appointment>  appointments = new ArrayList<>();
	
	public String toString(){return id.toString();}
}
