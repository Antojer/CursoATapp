package com.citas.citas.services.Room;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.citas.DAO.RoomDao;
import com.citas.DTO.RoomDTO;
import com.citas.model.Room;
import com.citas.services.Room.RoomService;
import com.citas.services.Room.RoomServiceImpl;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestRoomService {
	
	private static final Room room = new Room();
	private static final List<Room> rooms = new ArrayList<Room>();
	private Page<Room> roomPage;
	
	private static final RoomDTO roomDTO = new RoomDTO();
	
	private static Integer id = 1;
	private static Integer number = 6;
	
	@InjectMocks
	private RoomService service = new RoomServiceImpl();
	
	@Mock
	private Room p;
	
	@Mock
	private RoomDao roomDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	void roomStarter() {
		room.setId(id);
		room.setNumber(number);
		rooms.add(room);
		roomPage = new PageImpl<>(rooms);
	}
	
	void roomDTOStarter() {
		roomDTO.setId(id);
		roomDTO.setNumber(number);
	}
	
	void mockitos() {
		Mockito.when(roomDao.findAll(new PageRequest(0,10))).thenReturn(roomPage);
		Mockito.when(roomDao.findOne(1)).thenReturn(room);
		Mockito.when(mapper.map(room, RoomDTO.class)).thenReturn(
				new RoomDTO(room.getId(),room.getNumber()));
		Mockito.when(mapper.map(roomDTO, Room.class)).thenReturn(
				room);
		Mockito.when(roomDao.save(room)).thenReturn(room);
	}
	
	@Before
	public void Initializer() {
		roomStarter();
		roomDTOStarter();	
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<RoomDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(room), res.get(0));
	}
	

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(roomDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final RoomDTO res = service.findById(1);
		Assert.assertEquals(room.getId(), res.getId());
		Assert.assertEquals(room.getNumber(), res.getNumber());
		
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findById(90);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException {
		final RoomDTO res = service.create(roomDTO);
		Assert.assertEquals(room.getId(), id);
		Assert.assertEquals(room.getNumber(), res.getNumber());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException{
		service.create(new RoomDTO());
	}
	
	@Test
	public void testUpdateFine() throws NotFoundException {
		service.update(roomDTO);
	}
	
	@Test
	public void delete() {
		service.delete(id);
	}
}
