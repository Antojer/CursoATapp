package com.citas.services.Doctor;

import java.util.Date;
import java.util.List;

import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndWageDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.DTO.Doctor.DoctorTopDTO;
import com.citas.model.Doctor;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface DoctorService {

	/**
	 * Busca todos los médicos existentes
	 * 
	 * @return listado de médicos
	 * @throws NotFoundException 
	 */
	List<DoctorDTO> findAll(Integer pages,Integer size) throws NotFoundException;
	
	/**
	 * Transforma un DoctorDTO en un Doctor
	 * 
	 * @param d
	 * @return
	 *  
	 */
	Doctor transform(DoctorDTO d);

	/**
	 * Transforma un Doctor en un DoctorDTO
	 * 
	 * @param d
	 * @return
	 */
	DoctorDTO transform(Doctor d);
	
	/**
	 * Busca un médico por su Id
	 * 
	 * @param id
	 */
	DoctorDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Busca el médico con más pacientes
	 * 
	 * @param Integer
	 * @throws InvalidDataException 
	 */
	List<DoctorTopDTO> findTopNDoctors(Integer n) throws InvalidDataException;
	
	/**
	 * Busca las visitas médicas de un médico por su Id
	 * 
	 * @param id
	 */
	List<MedicalVisitDTO> getMedicalVisits(Integer id);
	
	/**
	 * Crea un médico
	 * 
	 * @param doctorDTO
	 */
	DoctorDTO create(DoctorDTO doctorDTO) throws InvalidDataException;
	
	/**
	 * Actualiza un médico
	 * 
	 * @param id, doctorDTO
	 */
	void update(DoctorDTO doctorDTO);
	
	/**
	 * Borra un médico
	 * 
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * Devuelve los médicos y lo que han ganado con las consultas entre dos fechas dadas
	 * 
	 * @param id, doctorDTO
	 * @throws NotFoundException 
	 */
	List<DoctorAndWageDTO> getDoctorStats(Date fIni,Date fFin) throws NotFoundException;
	
}
