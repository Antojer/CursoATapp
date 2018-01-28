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

import com.citas.DTO.RoomDTO;
import com.citas.services.Room.RoomService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RestController
@RequestMapping(value = "/api/room")
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping(method = {RequestMethod.GET})
	public List<RoomDTO> getAll(@PageableDefault(page=0,size=10)Pageable pageable) throws NotFoundException{
		return roomService.findAll(pageable.getPageNumber(),pageable.getPageSize());
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public RoomDTO getById(@PathVariable(value="id")Integer id) throws NotFoundException {
		return roomService.findById(id);
	}
	
	@RequestMapping(method = {RequestMethod.POST})
	public RoomDTO create(@RequestBody RoomDTO room) throws InvalidDataException {
		return roomService.create(room);
	}
	
	@RequestMapping(method = {RequestMethod.PUT})
	public void update(@RequestBody RoomDTO room) {
		roomService.update(room);
	}
	
	@RequestMapping(value = "/{id}", method = {RequestMethod.DELETE})
	public void delete(@PathVariable(value="id")Integer id) {
		roomService.delete(id);
	}
}
