package com.citas.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicDTO implements Serializable{

	private static final long serialVersionUID = -8607470358965868327L;
	
	private Integer id;
	
	private String name;
	
	public ClinicDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ClinicDTO() {}
	
}
