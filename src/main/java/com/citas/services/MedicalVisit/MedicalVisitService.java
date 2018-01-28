package com.citas.services.MedicalVisit;

import java.util.List;

import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.RoomDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.MedicalVisit;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface MedicalVisitService {
	/**
	 * Busca todas las consultas existentes
	 * 
	 * @return listado de consultas
	 * @throws NotFoundException 
	 */
	List<MedicalVisitDTO> findAll(Integer page, Integer size) throws NotFoundException;
	
	/**
	 * Transforma un MedicalVisitDTO en un MedicalVisit
	 * 
	 * @param m
	 * @return
	 * @throws NotFoundException 
	 */
	MedicalVisit transform(MedicalVisitDTO m) throws NotFoundException;

	/**
	 * Transforma un MedicalVisit en un MedicalVisitDTO
	 * 
	 * @param m
	 * @return
	 */
	MedicalVisitDTO transform(MedicalVisit m);
	
	/**
	 * Busca una consulta por su Id
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	MedicalVisitDTO findOne(Integer id) throws NotFoundException;

	/**
	 * Busca el médico que pasó la consulta
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	DoctorDTO getDoctor(Integer id) throws NotFoundException;
	
	/**
	 * Busca la sala en la que se pasó la consulta
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	RoomDTO getRoom(Integer id) throws NotFoundException;
	
	/**
	 * Crea una consulta
	 * 
	 * @param medicalVisitDTO
	 * @throws InvalidDataException 
	 * @throws NotFoundException 
	 */
	MedicalVisitDTO create(MedicalVisitDTO medicalVisitDTO) throws InvalidDataException, NotFoundException;
	
	/**
	 * Actualiza una consulta
	 * 
	 * @param id, medicalVisitDTO
	 * @throws NotFoundException 
	 */
	void update(MedicalVisitDTO medicalVisitDTO) throws NotFoundException;
	
	/**
	 * Borra una consulta
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
