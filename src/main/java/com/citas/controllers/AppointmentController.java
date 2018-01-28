package com.citas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citas.DTO.AppointmentDTO;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.PatientDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.services.Appointment.AppointmentService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<AppointmentDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable) throws NotFoundException{
		return appointmentService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public AppointmentDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return appointmentService.findById(id);
	}
	
	@RequestMapping(value = "/{id}/patient", method = {RequestMethod.GET})
	public PatientDTO getPatient(@PathVariable(value="id")Integer id) throws NotFoundException {
		return appointmentService.getPatient(id);
	}
	
	@RequestMapping(value = "/{id}/medicalVisit", method = {RequestMethod.GET})
	public MedicalVisitDTO getMedicalVisit(@PathVariable(value="id")Integer id) throws NotFoundException {
		return appointmentService.getMedicalVisit(id);
	}
	
	@RequestMapping(value = "/{id}/doctor", method = {RequestMethod.GET})
	public DoctorDTO getDoctor(@PathVariable(value="id")Integer id) throws NotFoundException {
		return appointmentService.getDoctor(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public AppointmentDTO create(@RequestBody AppointmentDTO appointment) throws InvalidDataException, NotFoundException {
		return appointmentService.create(appointment);
	}
	
	@RequestMapping(method = {RequestMethod.PUT})
	public void update(@RequestBody AppointmentDTO appointment) throws NotFoundException {
		appointmentService.update(appointment);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		appointmentService.delete(id);
	}
	
}
