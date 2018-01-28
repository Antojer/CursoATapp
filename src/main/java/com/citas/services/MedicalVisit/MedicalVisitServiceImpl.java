package com.citas.services.MedicalVisit;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.citas.DAO.MedicalVisitDao;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.RoomDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.Doctor;
import com.citas.model.MedicalVisit;
import com.citas.model.Room;
import com.citas.services.Doctor.DoctorService;
import com.citas.services.Room.RoomService;

import Exception.InvalidDataException;
import Exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicalVisitServiceImpl implements MedicalVisitService{
	
	@Autowired
	private MedicalVisitDao medicalVisitDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private DoctorService dService;
	
	@Autowired
	private RoomService rService;
	
	@Override
	public List<MedicalVisitDTO> findAll(Integer page, Integer size) throws NotFoundException{
		final Iterable<MedicalVisit> findAll = medicalVisitDao.findAll(new PageRequest(page,size));
		if(findAll!=null) {
			final List<MedicalVisitDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		throw new NotFoundException();
	}
	
	@Override
	public MedicalVisit transform(MedicalVisitDTO m) throws NotFoundException {
		MedicalVisit medicalVisit = medicalVisitDao.findOne(m.getId());
		if(medicalVisit == null)
			medicalVisit = new MedicalVisit();
		Doctor doctor = dService.transform(dService.findById(m.getDoctor_id()));
		Room room = rService.transform(rService.findById(m.getRoom_id()));
		medicalVisit.setDoctor(doctor);
		medicalVisit.setRoom(room);
		medicalVisit.setDate(m.getDate());
		medicalVisit.setTurn(m.getTurn());
		return medicalVisit;
	}

	@Override
	public MedicalVisitDTO transform(MedicalVisit m) {
		return mapper.map(m, MedicalVisitDTO.class);
	}
	
	@Override
	public MedicalVisitDTO findOne(Integer id) throws NotFoundException{
		final MedicalVisit m = medicalVisitDao.findOne(id);
		if(m!=null)
			return transform(m);
		throw new NotFoundException();
	}
	
	@Override
	public DoctorDTO getDoctor(Integer id) throws NotFoundException {
		final MedicalVisit mv = medicalVisitDao.findOne(id);
		if (mv != null)
			return dService.transform(mv.getDoctor());
		throw new NotFoundException();
	}
	
	@Override
	public RoomDTO getRoom(Integer id) throws NotFoundException {
		final MedicalVisit mv = medicalVisitDao.findOne(id);
		if (mv != null)
			return rService.transform(mv.getRoom());
		throw new NotFoundException();
	}
	
	@Override
	public MedicalVisitDTO create(MedicalVisitDTO medicalVisitDTO) throws InvalidDataException, NotFoundException{
		if(validate(medicalVisitDTO)) {
			log.info("Creando consulta");
			return transform(medicalVisitDao.save(transform(medicalVisitDTO)));
		}
		log.info("Error al crear consulta");
		throw new InvalidDataException("Datos incorrectos");
	}
	
	@Override
	public void update(MedicalVisitDTO medicalVisitDTO) throws NotFoundException{
		medicalVisitDao.save(transform(medicalVisitDTO));
	}
	
	private boolean validate(MedicalVisitDTO medicalVisitDTO) {
		return medicalVisitDTO.getDate()!=null && medicalVisitDTO.getRoom_id()!=null && medicalVisitDTO.getDoctor_id()!=null 
				&& medicalVisitDTO.getTurn()!=null;
	}
	
	@Override
	public void delete(Integer id) {
		medicalVisitDao.delete(id);
	}
}
