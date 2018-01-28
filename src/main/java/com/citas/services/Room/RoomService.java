package com.citas.services.Room;

import java.util.List;

import com.citas.DTO.RoomDTO;
import com.citas.model.Room;

import Exception.InvalidDataException;
import Exception.NotFoundException;

public interface RoomService {
	/**
	 * Busca todas las habitaciones existentes
	 * 
	 * @return listado de habitaciones
	 * @throws NotFoundException 
	 */
	List<RoomDTO> findAll(Integer page, Integer size) throws NotFoundException;
	
	/**
	 * Transforma un RoomDTO en un Room
	 * 
	 * @param c
	 * @return
	 */
	Room transform(RoomDTO r);

	/**
	 * Transforma un Room en un RoomDTO
	 * 
	 * @param c
	 * @return
	 */
	RoomDTO transform(Room r);
	
	/**
	 * Busca una habitación por su Id
	 * 
	 * @param id
	 * @throws NotFoundException 
	 */
	RoomDTO findById(Integer id) throws NotFoundException;
	
	/**
	 * Crea una habitación
	 * 
	 * @param roomDTO
	 * @throws InvalidDataException 
	 */
	RoomDTO create(RoomDTO roomDTO) throws InvalidDataException;
	
	/**
	 * Actualiza una habitación
	 * 
	 * @param roomDTO
	 */
	void update(RoomDTO roomDTO);
	
	/**
	 * Borra una habitación
	 * 
	 * @param id
	 */
	void delete(Integer id);
}
