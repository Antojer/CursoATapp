package com.citas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.dozer.Mapping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames = {"date","turn","room_id","doctor_id"}))
public class MedicalVisit implements Serializable{
	
	private static final long serialVersionUID = 143278341775803683L;

	@Id
	@GeneratedValue
	Integer id;
	
	@Mapping("doctor_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Doctor doctor;
	
	@Mapping("room_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Room room;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Enumerated(EnumType.STRING)
	private Turn turn;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="medicalVisit")
	private List<Appointment>  appointments = new ArrayList<>();
	
	public String toString(){return id.toString();}
}
