package com.citas.citas.services.MedicalVisit;

import java.util.ArrayList;
import java.util.Date;
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

import com.citas.DAO.MedicalVisitDao;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.RoomDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.Clinic;
import com.citas.model.Doctor;
import com.citas.model.MedicalVisit;
import com.citas.model.Room;
import com.citas.model.Turn;
import com.citas.services.Doctor.DoctorService;
import com.citas.services.MedicalVisit.MedicalVisitService;
import com.citas.services.MedicalVisit.MedicalVisitServiceImpl;
import com.citas.services.Room.RoomService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestMedicalVisitService {
	
	private static final MedicalVisit medicalVisit = new MedicalVisit();
	private static final List<MedicalVisit> medicalVisits = new ArrayList<MedicalVisit>();
	private Page<MedicalVisit> medicalVisitPage;
	
	private static final MedicalVisitDTO medicalVisitDTO = new MedicalVisitDTO();
	
	private static final Doctor doctor = new Doctor();
	private static final Room room = new Room();
	private static final Clinic clinic = new Clinic();
	
	private static final DoctorDTO doctorDTO = new DoctorDTO();
	private static final RoomDTO roomDTO = new RoomDTO();
	
	private static Integer id = 1;
	
	private static Integer doctor_id = 1;
	private static String doctor_name = "Rebeca";
	private static String doctor_surname = "Suárez";
	
	@InjectMocks
	private MedicalVisitService service = new MedicalVisitServiceImpl();
	
	@Mock
	private MedicalVisit a;
	
	@Mock
	private MedicalVisitDao medicalVisitDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private RoomService rService;
	
	@Mock
	private DoctorService dService;
	
	void doctorStarter() {
		doctor.setId(doctor_id);
		doctor.setName(doctor_name);
		doctor.setSurname(doctor_surname);
	}
	
	void doctorDTOStarter() {
		doctorDTO.setId(doctor_id);
		doctorDTO.setName(doctor_name);
		doctorDTO.setSurname(doctor_surname);
	}
	
	void roomStarter() {
		room.setId(1);
		room.setNumber(5);
	}
	
	void roomDTOStarter() {
		roomDTO.setId(1);
		roomDTO.setNumber(5);
	}
	
	void clinicStarter() {
		clinic.setId(1);
		clinic.setName("As Isa");
	}
	
	void medicalVisitStarter() {
		medicalVisit.setId(id);
		medicalVisit.setDate(new Date());
		medicalVisit.setRoom(room);
		medicalVisit.setTurn(Turn.T);
		medicalVisit.setDoctor(doctor);
		medicalVisits.add(medicalVisit);
		medicalVisitPage = new PageImpl<>(medicalVisits);
	}
	
	void medicalVisitDTOStarter() {
		medicalVisitDTO.setId(id);
		medicalVisitDTO.setDate(new Date());
		medicalVisitDTO.setRoom_id(room.getId());
		medicalVisitDTO.setTurn(Turn.T);
		medicalVisitDTO.setDoctor_id(doctor.getId());
	}
	
	
	void mockitos() throws NotFoundException{
		Mockito.when(medicalVisitDao.findAll(new PageRequest(0,10))).thenReturn(medicalVisitPage);
		Mockito.when(medicalVisitDao.findOne(1)).thenReturn(medicalVisit);
		Mockito.when(mapper.map(medicalVisit, MedicalVisitDTO.class)).thenReturn(
				new MedicalVisitDTO(medicalVisit.getId(),medicalVisit.getDate(),Turn.T,
						medicalVisit.getDoctor().getId(),medicalVisit.getRoom().getId()));
		Mockito.when(rService.transform(rService.findById(1))).thenReturn(room);
		Mockito.when(dService.transform(dService.findById(1))).thenReturn(doctor);
		Mockito.when(dService.transform(doctor)).thenReturn(doctorDTO);
		Mockito.when(rService.transform(room)).thenReturn(roomDTO);
		Mockito.when(medicalVisitDao.save(medicalVisit)).thenReturn(medicalVisit);
	}
	
	@Before
	public void Initializer() throws NotFoundException{
		clinicStarter();
		roomStarter();
		roomDTOStarter();
		medicalVisitStarter();	
		doctorStarter();
		doctorDTOStarter();
		medicalVisitStarter();
		medicalVisitDTOStarter();
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<MedicalVisitDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(medicalVisit), res.get(0));
	}
	

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(medicalVisitDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final MedicalVisitDTO res = service.findOne(1);
		Assert.assertEquals(medicalVisit.getId(), res.getId());
		Assert.assertEquals(medicalVisit.getDoctor().getId(), res.getDoctor_id());
		Assert.assertEquals(medicalVisit.getRoom().getId(), res.getRoom_id());
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findOne(90);
	}
	
	@Test
	public void testGetDoctorFine() throws NotFoundException {
		final DoctorDTO dDTO = service.getDoctor(1);
		Assert.assertEquals(doctorDTO.getId(), dDTO.getId());
		Assert.assertEquals(doctorDTO.getName(),dDTO.getName());
		Assert.assertEquals(doctorDTO.getSurname(),dDTO.getSurname());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetDoctorWrong() throws NotFoundException{
		service.getDoctor(99);
	}
	
	@Test
	public void testGetRoomFine() throws NotFoundException {
		final RoomDTO rDTO = service.getRoom(1);
		Assert.assertEquals(roomDTO.getId(), rDTO.getId());
		Assert.assertEquals(roomDTO.getNumber(),rDTO.getNumber());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetRoomWrong() throws NotFoundException{
		service.getRoom(99);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException, NotFoundException {
		final MedicalVisitDTO res = service.create(medicalVisitDTO);
		Assert.assertEquals(medicalVisit.getId(), id);
		Assert.assertEquals(medicalVisit.getDoctor().getId(), res.getDoctor_id());
		Assert.assertEquals(medicalVisit.getRoom().getId(), res.getRoom_id());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException, NotFoundException{
		service.create(new MedicalVisitDTO());
	}
	
	@Test
	public void testUpdateFine() throws NotFoundException {
		service.update(medicalVisitDTO);
	}
	
	@Test
	public void delete() {
		service.delete(id);
	}
}
