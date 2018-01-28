package com.citas.services.Clinic;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.citas.DAO.ClinicDao;
import com.citas.DTO.ClinicDTO;
import com.citas.model.Clinic;

import Exception.InvalidDataException;
import Exception.NotFoundException;


@Service
public class ClinicServiceImpl implements ClinicService{
	@Autowired
	private ClinicDao clinicDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public List<ClinicDTO> findAll(Integer page, Integer size) throws NotFoundException {
		final Iterable<Clinic> findAll = clinicDao.findAll(new PageRequest(page,size));
		if (findAll != null) {
			final List<ClinicDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		throw new NotFoundException();
	}
	
	@Override
	public Clinic transform(ClinicDTO a) {
		return mapper.map(a, Clinic.class);
	}

	@Override
	public ClinicDTO transform(Clinic a) {
		return mapper.map(a, ClinicDTO.class);
	}
	
	@Override
	public ClinicDTO findById(Integer id) throws NotFoundException{
		final Clinic a = clinicDao.findOne(id);
		if(a!=null)
			return transform(a);
		throw new NotFoundException();
	}
	
	@Override
	public ClinicDTO create(ClinicDTO clinicDTO) throws InvalidDataException{
		if(clinicDTO != null && clinicDTO.getName() != null)
			return transform(clinicDao.save(transform(clinicDTO)));
		throw new InvalidDataException("Datos Incorrectos");
	}
	
	@Override
	public void update(ClinicDTO clinicDTO){
		clinicDao.save(transform(clinicDTO));
	}
	
	@Override
	public void delete(Integer id) {
		clinicDao.delete(id);
	}
	
}
