package com.citas.DTO;

import java.io.Serializable;
import java.util.Date;

import com.citas.model.Turn;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalVisitDTO implements Serializable{

	private static final long serialVersionUID = -1760263907360240760L;
	private Integer id;
	private Integer room_id, doctor_id;
	private Date date;
	private Turn turn;
	
	public MedicalVisitDTO(Integer id, Date date, Turn turn, Integer room_id, Integer doctor_id) {
		this.id = id;
		this.date = date;
		this.turn = turn;
		this.room_id = room_id;
		this.doctor_id = doctor_id;
	}

	public MedicalVisitDTO() {}
}
