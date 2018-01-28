package com.citas.services.Patient;

import java.util.List;

import com.citas.DTO.PatientDTO;
import com.citas.model.Patient;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface PatientService {
	/**
	 * Busca todas las pacientes existentes
	 * 
	 * @return listado de pacientes
	 * @throws NotFoundException 
	 */
	List<PatientDTO> findAll(Integer page, Integer size) throws NotFoundException;
	
	/**
	 * Transforma un PatientDTO en un Patient
	 * 
	 * @param c
	 * @return
	 */
	Patient transform(PatientDTO c);

	/**
	 * Transforma un Patient en un PatientDTO
	 * 
	 * @param c
	 * @return
	 */
	PatientDTO transform(Patient c);
	
	/**
	 * Busca un paciente por su Id
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	PatientDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Crea un paciente
	 * 
	 * @param patientDTO
	 * @throws InvalidDataException 
	 */
	PatientDTO create(PatientDTO patientDTO) throws InvalidDataException;
	
	/**
	 * Actualiza un paciente
	 * 
	 * @param id, patientDTO
	 */
	void update(PatientDTO patientDTO);
	
	/**
	 * Borra un paciente
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
