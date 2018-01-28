package com.citas.services.Appointment;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.citas.DAO.AppointmentDao;
import com.citas.DTO.AppointmentDTO;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.PatientDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.Appointment;
import com.citas.model.MedicalVisit;
import com.citas.model.Patient;
import com.citas.services.Doctor.DoctorService;
import com.citas.services.MedicalVisit.MedicalVisitService;
import com.citas.services.Patient.PatientService;

import Exception.InvalidDataException;
import Exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Autowired
	private PatientService ptService;
	
	@Autowired
	private MedicalVisitService mvService;
	
	@Autowired
	private DoctorService dService;
	
	@Override
	public List<AppointmentDTO> findAll(Integer page, Integer size) throws NotFoundException{
		log.info("se van a buscar todas las citas...");
		final Iterable<Appointment> findAll = appointmentDao.findAll(new PageRequest(page,size));
		if(findAll!=null) {
			final List<AppointmentDTO> res = new ArrayList<>();
			findAll.forEach(d -> {
				res.add(transform(d));
			});
			return res;
		}
		log.info("Excepción: No se han encontrado citas");
		throw new NotFoundException();
	}
	
	@Override
	public Appointment transform(AppointmentDTO a) throws NotFoundException{
		Appointment appointment = appointmentDao.findOne(a.getId());
		if(appointment == null)
			appointment = new Appointment();
		MedicalVisit medicalvisit = mvService.transform(mvService.findOne(a.getMedicalVisit()));
		Patient patient = ptService.transform(ptService.findById(a.getPatient()));
		appointment.setMedicalVisit(medicalvisit);
		appointment.setPatient(patient);
		return appointment;
	}

	@Override
	public AppointmentDTO transform(Appointment a) {
		return mapper.map(a, AppointmentDTO.class);
	}
	
	@Override
	public AppointmentDTO findById(Integer id) throws NotFoundException{
		log.info("se va a buscar la cita con id con "+id);
		final Appointment a = appointmentDao.findOne(id);
		if(a!=null)
			return transform(a);
		log.info("Excepción: no se ha encontrado");
		throw new NotFoundException();
	}
	
	@Override
	public PatientDTO getPatient(Integer id) throws NotFoundException{
		log.info("Obteniendo el paciente de la cita con id"+id);
		final Appointment a = appointmentDao.findOne(id);
		if(a != null)
			return ptService.transform(a.getPatient());
		log.info("Excepción: No se ha encontrado la cita");
		throw new NotFoundException();
	}
	
	@Override
	public MedicalVisitDTO getMedicalVisit(Integer id) throws NotFoundException {
		log.info("Obteniendo la consulta de la cita con id "+id);
		final Appointment a = appointmentDao.findOne(id);
		if (a != null)
			return mvService.transform(a.getMedicalVisit());
		log.info("Excepción: No se ha encontrado la cita");
		throw new NotFoundException();
	}
	
	@Override
	public DoctorDTO getDoctor(Integer id) throws NotFoundException {
		log.info("Obteniendo el doctor con el que se tenía la cita con id"+id);
		final Appointment a = appointmentDao.findOne(id);
		if (a != null)
			return dService.transform(a.getMedicalVisit().getDoctor());
		log.info("Excepción: No se ha encontrado la cita");
		throw new NotFoundException();
	}
	
	@Override
	public AppointmentDTO create(AppointmentDTO appointmentDTO) throws InvalidDataException, NotFoundException {
		log.info("Intentando crear la cita...");
		if(validate(appointmentDTO))
			return transform(appointmentDao.save(transform(appointmentDTO)));
		log.info("Excepción: Alguno de los campos no contiene datos correctos o están vacios");
		throw new InvalidDataException("Datos incorrectos");
	}
	
	@Override
	public void update(AppointmentDTO appointmentDTO) throws NotFoundException {
		log.info("Actualizando cita...");
		appointmentDao.save(transform(appointmentDTO));
	}
	
	@Override
	public void delete(Integer id) {
		log.info("Borrando cita...");
		appointmentDao.delete(id);
	}
	
	public boolean validate(AppointmentDTO d) {
		return d!= null && d.getPatient() != null && d.getMedicalVisit() != null;
	}
}
