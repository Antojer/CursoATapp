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
public class Clinic implements Serializable {

	private static final long serialVersionUID = -6955990520607722187L;

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="clinic")
	private List<Room>  rooms = new ArrayList<>();
	
	public String toString(){return id.toString();}
}
