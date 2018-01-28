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

import com.citas.DTO.ClinicDTO;
import com.citas.services.Clinic.ClinicService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/clinic")
public class ClinicController {

	@Autowired
	private ClinicService clinicService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<ClinicDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable) throws NotFoundException{
		return clinicService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public ClinicDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return clinicService.findById(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public ClinicDTO create(@RequestBody ClinicDTO clinic) throws InvalidDataException {
		return clinicService.create(clinic);
	}
	
	@RequestMapping(method = {RequestMethod.PUT})
	public void update(@RequestBody ClinicDTO clinic) {
		clinicService.update(clinic);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		clinicService.delete(id);
	}
}
