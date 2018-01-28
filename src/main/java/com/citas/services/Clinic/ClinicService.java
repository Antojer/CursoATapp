package com.citas.services.Clinic;

import java.util.List;

import com.citas.DTO.ClinicDTO;
import com.citas.model.Clinic;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface ClinicService {
	/**
	 * Busca todas las clínicas existentes
	 * 
	 * @return listado de clínicas
	 * @throws NotFoundException 
	 */
	List<ClinicDTO> findAll(Integer page, Integer size) throws NotFoundException;
	
	/**
	 * Transforma un ClinicDTO en un Clinic
	 * 
	 * @param c
	 * @return
	 */
	Clinic transform(ClinicDTO c);

	/**
	 * Transforma un Clinic en un ClinicDTO
	 * 
	 * @param c
	 * @return
	 */
	ClinicDTO transform(Clinic c);
	
	/**
	 * Busca una clínica por su Id
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	ClinicDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Crea una clínica
	 * 
	 * @param clinicDTO
	 * @throws InvalidDataException 
	 */
	ClinicDTO create(ClinicDTO clinicDTO) throws InvalidDataException;
	
	/**
	 * Actualiza una clínica
	 * 
	 * @param clinicDTO
	 * 
	 */
	void update(ClinicDTO clinicDTO);
	
	/**
	 * Borra una cliínica
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
