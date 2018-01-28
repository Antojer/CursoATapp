package com.citas.citas.services.Patient;

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

import com.citas.DAO.PatientDao;
import com.citas.DTO.PatientDTO;
import com.citas.model.Patient;
import com.citas.services.Patient.PatientService;
import com.citas.services.Patient.PatientServiceImpl;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestPatientService {
	
	private static final Patient patient = new Patient();
	private static final List<Patient> patients = new ArrayList<Patient>();
	private Page<Patient> patientPage;
	
	private static final PatientDTO patientDTO = new PatientDTO();
	
	private static Integer id = 1;
	private static String name = "Wally";
	private static String surname = "TheLost";
	
	@InjectMocks
	private PatientService service = new PatientServiceImpl();
	
	@Mock
	private Patient p;
	
	@Mock
	private PatientDao patientDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	void patientStarter() {
		patient.setId(id);
		patient.setName(name);
		patient.setSurname(surname);
		patients.add(patient);
		patientPage = new PageImpl<>(patients);
	}
	
	void patientDTOStarter() {
		patientDTO.setId(id);
		patientDTO.setName(name);
		patientDTO.setSurname(surname);
	}
	
	void mockitos() {
		Mockito.when(patientDao.findAll(new PageRequest(0,10))).thenReturn(patientPage);
		Mockito.when(patientDao.findOne(1)).thenReturn(patient);
		Mockito.when(mapper.map(patient, PatientDTO.class)).thenReturn(
				new PatientDTO(patient.getId(),patient.getName(),patient.getSurname()));
		Mockito.when(mapper.map(patientDTO, Patient.class)).thenReturn(
				patient);
		Mockito.when(patientDao.save(patient)).thenReturn(patient);
	}
	
	@Before
	public void Initializer() {
		patientStarter();
		patientDTOStarter();	
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<PatientDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(patient), res.get(0));
	}
	

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(patientDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final PatientDTO res = service.findById(1);
		Assert.assertEquals(patient.getName(), res.getName());
		Assert.assertEquals(patient.getSurname(), res.getSurname());
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findById(90);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException {
		final PatientDTO res = service.create(patientDTO);
		Assert.assertEquals(patient.getId(), id);
		Assert.assertEquals(patient.getName(), res.getName());
		Assert.assertEquals(patient.getSurname(), res.getSurname());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException{
		service.create(new PatientDTO());
	}
	
	@Test
	public void testUpdateFine() throws NotFoundException {
		service.update(patientDTO);
	}
	
	@Test
	public void delete() {
		service.delete(id);
	}
	
}