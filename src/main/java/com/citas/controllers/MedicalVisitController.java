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

import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.RoomDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.services.MedicalVisit.MedicalVisitService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/medicalVisit")
public class MedicalVisitController {

	@Autowired
	private MedicalVisitService medicalVisitService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<MedicalVisitDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable) throws NotFoundException{
		return medicalVisitService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public MedicalVisitDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return medicalVisitService.findOne(id);
	}
	
	@RequestMapping(value = "/{id}/doctor", method = {RequestMethod.GET})
	public DoctorDTO getDoctor(@PathVariable(value="id")Integer id) throws NotFoundException {
		return medicalVisitService.getDoctor(id);
	}
	
	@RequestMapping(value = "/{id}/room", method = {RequestMethod.GET})
	public RoomDTO getRoom(@PathVariable(value="id")Integer id) throws NotFoundException {
		return medicalVisitService.getRoom(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public MedicalVisitDTO create(@RequestBody MedicalVisitDTO medicalVisit) throws InvalidDataException, NotFoundException {
		return medicalVisitService.create(medicalVisit);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
	public void update(@PathVariable(value="id") Integer id, @RequestBody MedicalVisitDTO medicalVisit) throws NotFoundException {
		medicalVisitService.update(medicalVisit);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		medicalVisitService.delete(id);
	}
}
