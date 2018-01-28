package com.citas.services.Room;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.citas.DAO.RoomDao;
import com.citas.DTO.RoomDTO;
import com.citas.model.Room;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@Service
public class RoomServiceImpl implements RoomService{
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<RoomDTO> findAll(Integer page, Integer size)throws NotFoundException {
		final Iterable<Room> findAll = roomDao.findAll(new PageRequest(page, size));
		if(findAll != null) {
			final List<RoomDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		throw new NotFoundException();
	}
	
	@Override
	public Room transform(RoomDTO r) {
		return mapper.map(r, Room.class);
	}

	@Override
	public RoomDTO transform(Room r) {
		return mapper.map(r, RoomDTO.class);
	}
	
	@Override
	public RoomDTO findById(Integer id) throws NotFoundException{
		final Room r = roomDao.findOne(id);
		if(r != null)
			return transform(r);
		throw new NotFoundException();
	}
	
	@Override
	public RoomDTO create(RoomDTO roomDTO) throws InvalidDataException{
		if(roomDTO.getNumber()!=null)
			return transform(roomDao.save(transform(roomDTO)));
		throw new InvalidDataException("Datos Incorrectos");
	}
	
	@Override
	public void update(RoomDTO roomDTO) {
		roomDao.save(transform(roomDTO));
	}
	
	@Override
	public void delete(Integer id) {
		roomDao.delete(id);
	}
}
