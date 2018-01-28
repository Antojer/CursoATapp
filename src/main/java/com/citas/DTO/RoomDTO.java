package com.citas.DTO;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO implements Serializable {

	private static final long serialVersionUID = -1303610745691952877L;
	
	Integer id,number;
	
	public RoomDTO(Integer id,Integer number) {
		this.id = id;
		this.number = number;
	}
	
	public RoomDTO() {}
}
