package com.citas.citas.services.Doctor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

import com.citas.DAO.DoctorDao;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndTotalMedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndWageDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.DTO.Doctor.DoctorDTOExternalApi;
import com.citas.DTO.Doctor.DoctorTopDTO;
import com.citas.model.Doctor;
import com.citas.model.MedicalVisit;
import com.citas.services.Doctor.DoctorService;
import com.citas.services.Doctor.DoctorServiceImpl;

import Exception.InvalidDataException;
import Exception.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class TestDoctorService {
	
	private static final Doctor doctor = new Doctor();
	private static final List<Doctor> doctors = new ArrayList<Doctor>();
	private Page<Doctor> doctorsPage ;
	
	private static final DoctorDTO doctorDTO = new DoctorDTO();
	private static final List<DoctorDTO> doctorsDTO = new ArrayList<DoctorDTO>();
	
	private static final DoctorTopDTO doctorTop = new DoctorTopDTO();
	private static final List<DoctorTopDTO> doctorTops = new ArrayList<DoctorTopDTO>();
		
	private static final List<MedicalVisit> mv = new ArrayList<MedicalVisit>();
	private static final List<MedicalVisitDTO> mvDTO = new ArrayList<MedicalVisitDTO>();
	
	private static final DoctorAndTotalMedicalVisitDTO dtmvDTO = new DoctorAndTotalMedicalVisitDTO();
	private static final List<DoctorAndTotalMedicalVisitDTO> dtmvDTOs = new ArrayList<>();
	private static final DoctorAndWageDTO dwDTO = new DoctorAndWageDTO();
	private static final DoctorDTOExternalApi deapiDTO = new DoctorDTOExternalApi();
	
	//private ResponseEntity response = mock(ResponseObject.class);
	private static final Object o = new Object();
	
	private static Integer id = 1;
	private static String name = "Rebeca";
	private static String surname = "Suárez";
	private static String externalId = "12345LF";
	private static Date date = new Date();
	
	@InjectMocks
	private DoctorService service = new DoctorServiceImpl();
	
	@Mock
	private Doctor d;
	
	@Mock
	private DoctorDao doctorDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private RestTemplate restTemplate;
	
	void doctorStarter() {
		doctor.setId(id);
		doctor.setName(name);
		doctor.setSurname(surname);
		doctor.setExternalApiId(externalId);
		doctors.add(doctor);
		doctorsPage = new PageImpl<>(doctors);
	}
	
	void doctorDTOStarter() {
		doctorDTO.setId(id);
		doctorDTO.setName(name);
		doctorDTO.setSurname(surname);
		doctorsDTO.add(doctorDTO);
	}
	
	void doctorTopDTOStarter() {
		doctorTop.setId(id);
		doctorTop.setName(name);
		doctorTop.setSurname(surname);
		doctorTop.setExternalApiId(externalId);
		doctorTop.setTotalPatients(33l);
		doctorTops.add(doctorTop);
	}
	
	void doctorTotalMVDTOStarter() {
		dtmvDTO.setId(id);
		dtmvDTO.setName(name);
		dtmvDTO.setSurname(surname);
		dtmvDTO.setMedicalVisitTotal(2l);
		dtmvDTOs.add(dtmvDTO);
	}
	
	void doctorAndWageDTOStarter() {
		dwDTO.setId(id);
		dwDTO.setName(name);
		dwDTO.setSurname(surname);
		dwDTO.setWage(47f);	
	}
	
	void doctorEApiDTOStarter() {
		deapiDTO.setId(externalId);
		deapiDTO.setName(name);
		deapiDTO.setPrice(23.5f);
	}
	
	@SuppressWarnings("unchecked") //Matchers.any(Class.class)
	void mockitos() {
		Mockito.when(doctorDao.findAll(new PageRequest(0,10))).thenReturn(doctorsPage);
		Mockito.when(doctorDao.findOne(1)).thenReturn(doctor);
		Mockito.when(mapper.map(doctor, DoctorDTO.class)).thenReturn(
				new DoctorDTO(doctor.getId(),doctor.getName(),doctor.getSurname(),"1234LA"));
		Mockito.when(mapper.map(doctorDTO, Doctor.class)).thenReturn(
				doctor);
		Mockito.when(doctorDao.save(doctor)).thenReturn(doctor);
		Mockito.when(d.getMedicalvisits()).thenReturn(mv);
		Mockito.doReturn(doctorTops).when(doctorDao).findTopNDoctors(new PageRequest(0,1));
		Mockito.when(doctorDao.getDoctorByMedicalVisitDate(date, date)).thenReturn(dtmvDTOs);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(),Matchers.any(Class.class))).thenReturn(o);
		Mockito.when(mapper.map(o,DoctorDTOExternalApi.class)).thenReturn(deapiDTO);
	}
	
	@Before
	public void Initializer() {
		doctorStarter();
		doctorDTOStarter();
		doctorTopDTOStarter();
		doctorTotalMVDTOStarter();
		doctorAndWageDTOStarter();
		doctorEApiDTOStarter();
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<DoctorDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(doctor), res.get(0));
	}

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(doctorDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final DoctorDTO res = service.findById(1);
		Assert.assertEquals(doctor.getName(), res.getName());
		Assert.assertEquals(doctor.getSurname(), res.getSurname());
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findById(90);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException {
		final DoctorDTO res = service.create(doctorDTO);
		Assert.assertEquals(doctor.getId(), id);
		Assert.assertEquals(doctor.getName(), res.getName());
		Assert.assertEquals(doctor.getSurname(), res.getSurname());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException{
		service.create(new DoctorDTO());
	}
	
	@Test
	public void testUpdateFine() {
		service.update(doctorDTO);
	}
	
	@Test
	public void testDeleteFine() {
		service.delete(d.getId());
	}
	
	@Test
	public void testGetMedicalVisitsFine() {
		final List<MedicalVisitDTO> medicalVisit = service.getMedicalVisits(id);
		Assert.assertEquals(mvDTO, medicalVisit);
	}
	
	
	@Test
	public void testGetTopNDoctorsFine() throws InvalidDataException {
		List<DoctorTopDTO> d = service.findTopNDoctors(1);
 		Assert.assertEquals(doctorTop.getName(), d.get(0).getName());
 		Assert.assertEquals(doctorTop.getSurname(), d.get(0).getSurname());
 		Assert.assertEquals(doctorTop.getExternalApiId(), d.get(0).getExternalApiId());
 		Assert.assertEquals(doctorTop.getTotalPatients(), d.get(0).getTotalPatients());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testGetTopNDoctorsWrong() throws InvalidDataException{
		service.findTopNDoctors(null);
	}
	
	@Test
	public void getDoctorStats() throws NotFoundException {
		List<DoctorAndWageDTO> dw = service.getDoctorStats(date, date);
		Assert.assertEquals(dwDTO.getId(), dw.get(0).getId());
		Assert.assertEquals(dwDTO.getName(), dw.get(0).getName());
		Assert.assertEquals(dwDTO.getSurname(), dw.get(0).getSurname());
		Assert.assertEquals(dwDTO.getWage(),dw.get(0).getWage(),0);
	}
	
}
