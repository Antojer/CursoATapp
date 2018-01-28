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

import com.citas.DTO.PatientDTO;
import com.citas.services.Patient.PatientService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<PatientDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable) throws NotFoundException{
		return patientService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public PatientDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return patientService.findById(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public PatientDTO create(@RequestBody PatientDTO patient) throws InvalidDataException {
		return patientService.create(patient);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
	public void update(@PathVariable(value="id")Integer id,@RequestBody PatientDTO patient) {
		patientService.update(patient);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		patientService.delete(id);
	}
}
