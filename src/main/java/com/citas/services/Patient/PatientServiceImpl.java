package com.citas.services.Patient;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.citas.DAO.PatientDao;
import com.citas.DTO.PatientDTO;
import com.citas.model.Patient;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@Service
public class PatientServiceImpl implements PatientService{
	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<PatientDTO> findAll(Integer page, Integer size) throws NotFoundException {
		final Iterable<Patient> findAll = patientDao.findAll(new PageRequest(page,size));
		if(findAll != null) {
			final List<PatientDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		throw new NotFoundException();
	}
	
	@Override
	public Patient transform(PatientDTO p) {
		return mapper.map(p, Patient.class);
	}

	@Override
	public PatientDTO transform(Patient p) {
		return mapper.map(p, PatientDTO.class);
	}
	
	@Override
	public PatientDTO findById(Integer id) throws NotFoundException {
		final Patient p = patientDao.findOne(id);
		if(p!=null)
			return transform(p);
		throw new NotFoundException();
	}
	
	@Override
	public PatientDTO create(PatientDTO patientDTO)throws InvalidDataException{
		if(validate(patientDTO))
			return transform(patientDao.save(transform(patientDTO)));
		throw new InvalidDataException("Datos Incorrectos");
	}
	
	@Override
	public void update(PatientDTO patientDTO) {
		patientDao.save(transform(patientDTO));
	}
	
	@Override
	public void delete(Integer id) {
		patientDao.delete(id);
	}
	
	public boolean validate(PatientDTO d) {
		return d!= null && d.getName() != null && d.getSurname() != null;
	}
}
