package com.citas.services.Appointment;

import java.util.List;

import com.citas.DTO.AppointmentDTO;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.PatientDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.Appointment;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface AppointmentService {

	/**
	 * Busca todas las citas existentes
	 * 
	 * @return listado de citas
	 * @throws NotFoundException 
	 */
	List<AppointmentDTO> findAll(Integer page, Integer size) throws NotFoundException;
	
	/**
	 * Transforma un AppointmentDTO en un Appointment
	 * 
	 * @param a
	 * @return
	 * @throws NotFoundException 
	 */
	Appointment transform(AppointmentDTO a) throws NotFoundException;

	/**
	 * Transforma un Appointment en un AppointmentDTO
	 * 
	 * @param a
	 * @return
	 */
	AppointmentDTO transform(Appointment a);
	
	/**
	 * Busca una cita por su Id
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	AppointmentDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Busca el paciente relacionado con ésta cita
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	PatientDTO getPatient(Integer id) throws NotFoundException;
	
	/**
	 * Busca la consulta relacionada con ésta cita
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	MedicalVisitDTO getMedicalVisit(Integer id) throws NotFoundException;
	
	/**
	 * Devuelve el doctor con el que se tenía la cita
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	DoctorDTO getDoctor(Integer id) throws NotFoundException;
	
	/**
	 * Crea una cita
	 * 
	 * @param appointmentDTO
	 * @throws InvalidDataException 
	 * @throws NotFoundException 
	 */
	AppointmentDTO create(AppointmentDTO appointmentDTO) throws InvalidDataException, NotFoundException;
	
	/**
	 * Actualiza una cita
	 * 
	 * @param id, appointmentDTO
	 * @throws NotFoundException 
	 */
	void update(AppointmentDTO appointmentDTO) throws NotFoundException;
	
	/**
	 * Borra una cita
	 * 
	 * @param id
	 */
	void delete(Integer id);

}
