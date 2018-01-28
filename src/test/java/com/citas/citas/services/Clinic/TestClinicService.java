package com.citas.citas.services.Clinic;

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

import com.citas.DAO.ClinicDao;
import com.citas.DTO.ClinicDTO;
import com.citas.model.Clinic;
import com.citas.services.Clinic.ClinicService;
import com.citas.services.Clinic.ClinicServiceImpl;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestClinicService {
	
	private static final Clinic clinic = new Clinic();
	private static final List<Clinic> clinics = new ArrayList<Clinic>();
	private Page<Clinic> clinicPage;
	
	private static final ClinicDTO clinicDTO = new ClinicDTO();
	
	private static Integer id = 1;
	private static String name = "Matasanos";
	
	@InjectMocks
	private ClinicService service = new ClinicServiceImpl();
	
	@Mock
	private Clinic p;
	
	@Mock
	private ClinicDao clinicDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	void clinicStarter() {
		clinic.setId(id);
		clinic.setName(name);
		clinics.add(clinic);
		clinicPage = new PageImpl<>(clinics);
	}
	
	void clinicDTOStarter() {
		clinicDTO.setId(id);
		clinicDTO.setName(name);
	}
	
	void mockitos() {
		Mockito.when(clinicDao.findAll(new PageRequest(0,10))).thenReturn(clinicPage);
		Mockito.when(clinicDao.findOne(1)).thenReturn(clinic);
		Mockito.when(mapper.map(clinic, ClinicDTO.class)).thenReturn(
				new ClinicDTO(clinic.getId(),clinic.getName()));
		Mockito.when(mapper.map(clinicDTO, Clinic.class)).thenReturn(
				clinic);
		Mockito.when(clinicDao.save(clinic)).thenReturn(clinic);
	}
	
	@Before
	public void Initializer() {
		clinicStarter();
		clinicDTOStarter();	
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<ClinicDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(clinic), res.get(0));
	}
	

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(clinicDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final ClinicDTO res = service.findById(1);
		Assert.assertEquals(clinic.getId(), id);
		Assert.assertEquals(clinic.getName(), res.getName());
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findById(90);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException {
		final ClinicDTO res = service.create(clinicDTO);
		Assert.assertEquals(clinic.getId(), id);
		Assert.assertEquals(clinic.getName(), res.getName());
	}
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException{
		service.create(new ClinicDTO());
	}
	
	@Test
	public void testUpdateFine() throws NotFoundException {
		service.update(clinicDTO);
	}
	
	@Test
	public void delete() {
		service.delete(id);
	}
}
