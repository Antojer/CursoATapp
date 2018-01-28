package com.citas.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndWageDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.DTO.Doctor.DoctorTopDTO;
import com.citas.services.Doctor.DoctorService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/doctor")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<DoctorDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable)
			throws NotFoundException{
		
		return doctorService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public DoctorDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return doctorService.findById(id);
	}
	
	@RequestMapping(value = "/popularDoctors/{max}", method = {RequestMethod.GET})
	public List<DoctorTopDTO> getMostValuedDoctor(@PathVariable(value="max")Integer n) throws InvalidDataException{
		return doctorService.findTopNDoctors(n);
	}
	
	@RequestMapping(value = "/{id}/medicalVisits", method = {RequestMethod.GET})
	public List<MedicalVisitDTO> getMedicalVisits(@PathVariable(value="id")Integer id) throws NotFoundException {
		return doctorService.getMedicalVisits(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public DoctorDTO create(@RequestBody DoctorDTO doctor) throws InvalidDataException{
		return doctorService.create(doctor);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.PUT})
	public void update(@RequestBody DoctorDTO doctor) {
		doctorService.update(doctor);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		doctorService.delete(id);
	}
	
	@RequestMapping(value = "/stats")
	public List<DoctorAndWageDTO> getDoctorStats(@RequestParam(value = "fIni")@DateTimeFormat(pattern="dd-MM-yyyy")Date fIni,
			@RequestParam(value = "fFin")@DateTimeFormat(pattern="dd-MM-yyyy")Date fFin) throws NotFoundException {
		return doctorService.getDoctorStats(fIni,fFin);		
	}
}
	
