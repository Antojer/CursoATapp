package com.citas.services.Doctor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.citas.DAO.DoctorDao;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndTotalMedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorAndWageDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.DTO.Doctor.DoctorDTOExternalApi;
import com.citas.DTO.Doctor.DoctorTopDTO;
import com.citas.model.Doctor;
import com.citas.model.MedicalVisit;
import com.citas.services.MedicalVisit.MedicalVisitService;

import Exception.InvalidDataException;
import Exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService{
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private MedicalVisitService mvService;
	
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public List<DoctorDTO> findAll(Integer pages, Integer size) throws NotFoundException{
		log.info("se van a buscar todos los doctores...");
		final Iterable<Doctor> findAll = doctorDao.findAll(new PageRequest(pages,size));
		if(findAll != null) {
			final List<DoctorDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		log.info("Excepción: No se han encontrado doctores");
		throw new NotFoundException();
	}
	
	@Override
	public Doctor transform(DoctorDTO d) {
		return mapper.map(d, Doctor.class);
	}
	
	@Override
	public DoctorDTO transform(Doctor d) {
		return mapper.map(d, DoctorDTO.class);
	}
	
	@Override
	public DoctorDTO findById(Integer id) throws NotFoundException{
		log.info("se va a buscar el médico con id con "+id);
		final Doctor d = doctorDao.findOne(id);
		if(d != null)
			return transform(d);
		log.info("Excepción: no se ha encontrado");
		throw new NotFoundException();
	}
	
	@Override
	public List<DoctorTopDTO> findTopNDoctors(Integer n) throws InvalidDataException{
		log.info("Obteniendo el top "+n+" de médicos con más pacientes");
		if( n!=null ) {
			final List<DoctorTopDTO> res = doctorDao.findTopNDoctors(new PageRequest(0,n));
			return res;
		}
		throw new InvalidDataException("Error debe elegir de cuánto será el top");
	}
	
	@Override
	public List<MedicalVisitDTO> getMedicalVisits(Integer id) {
		log.info("Obteniendo las consultas que ha pasado el/la doctor/a con id"+id);
		final List<MedicalVisit> a = doctorDao.findOne(id).getMedicalvisits();
		final List<MedicalVisitDTO> res = new ArrayList<>();
		a.forEach(d -> {
			res.add(mvService.transform(d));
		});
		return res;
	}

	
	@Override
	public DoctorDTO create(DoctorDTO doctorDTO) throws InvalidDataException{
		log.info("Intentando crear el/la doctor/a...");
		if(validate(doctorDTO))
			return transform(doctorDao.save(transform(doctorDTO)));
		log.info("Excepción: Alguno de los campos no contiene datos correctos o están vacios");
		throw new InvalidDataException("Datos incorrectos");
	}
	
	@Override
	public void update(DoctorDTO doctorDTO) {
		log.info("Actualizando doctor/a...");
		doctorDao.save(transform(doctorDTO));
	}
	
	@Override
	public void delete(Integer id) {
		log.info("Borrando doctor/a...");
		doctorDao.delete(id);
	}
	
	public boolean validate(DoctorDTO d) {
		return d!= null && d.getName() != null && d.getSurname() != null;
	}
	
	@Override
	public List<DoctorAndWageDTO> getDoctorStats(Date fIni,Date fFin) throws NotFoundException {
		List<DoctorAndTotalMedicalVisitDTO> doctorMedicalVisits = 
				doctorDao.getDoctorByMedicalVisitDate(fIni,fFin);
		List<DoctorAndWageDTO> res = new ArrayList<>();
		doctorMedicalVisits.forEach(dmv ->{
			log.info("Obteniendo precio por consulta del/la doctor/a "+dmv.getId());
			final Object o = restTemplate.getForObject("http://doctor.dbgjerez.es:8080/api/doctor/"+dmv.getExternalApiId(), 
					DoctorDTOExternalApi.class);
			final DoctorDTOExternalApi oFinal = mapper.map(o, DoctorDTOExternalApi.class);
			res.add(new DoctorAndWageDTO(dmv.getId(), dmv.getName(),dmv.getSurname(),
					(dmv.getMedicalVisitTotal()*oFinal.getPrice())));
		});
		return res;
	}
}
