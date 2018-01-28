package com.citas.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.citas.model.Appointment;

public interface AppointmentDao extends PagingAndSortingRepository<Appointment,Integer>{
	
}
