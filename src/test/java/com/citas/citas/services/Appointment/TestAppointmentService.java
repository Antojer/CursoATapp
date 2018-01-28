package com.citas.citas.services.Appointment;

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

import com.citas.DAO.AppointmentDao;
import com.citas.DTO.AppointmentDTO;
import com.citas.DTO.MedicalVisitDTO;
import com.citas.DTO.PatientDTO;
import com.citas.DTO.Doctor.DoctorDTO;
import com.citas.model.Appointment;
import com.citas.model.Clinic;
import com.citas.model.Doctor;
import com.citas.model.MedicalVisit;
import com.citas.model.Patient;
import com.citas.model.Room;
import com.citas.model.Turn;
import com.citas.services.Appointment.AppointmentService;
import com.citas.services.Appointment.AppointmentServiceImpl;
import com.citas.services.Doctor.DoctorService;
import com.citas.services.MedicalVisit.MedicalVisitService;
import com.citas.services.Patient.PatientService;

import Exception.InvalidDataException;
import Exception.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class TestAppointmentService {

	private static final Appointment appointment = new Appointment();
	private static final List<Appointment> appointments = new ArrayList<Appointment>();
	private Page<Appointment> appointmentPage;
	
	private static final AppointmentDTO appointmentDTO = new AppointmentDTO();
	
	private static final MedicalVisit medicalVisit = new MedicalVisit();
	private static final MedicalVisitDTO medicalVisitDTO = new MedicalVisitDTO();
	private static final Patient patient = new Patient();
	private static final PatientDTO patientDTO = new PatientDTO();
	private static final Doctor doctor = new Doctor();
	private static final DoctorDTO doctorDTO = new DoctorDTO();
	private static final Room room = new Room();
	private static final Clinic clinic = new Clinic();
	 
	private static Integer id = 1;
	
	private static Integer patient_id = 1;
	private static String patient_name = "Pablo";
	private static String patient_surname = "Escobar";
	
	private static Integer doctor_id = 1;
	private static String doctor_name = "Rebeca";
	private static String doctor_surname = "Suárez";
	
	@InjectMocks
	private AppointmentService service = new AppointmentServiceImpl();
	
	@Mock
	private Appointment a;
	
	@Mock
	private AppointmentDao appointmentDao;
	
	@Mock
	private DozerBeanMapper mapper;
	
	@Mock
	private PatientService ptService;
	
	@Mock
	private MedicalVisitService mvService;
	
	@Mock
	private DoctorService dService;
	
	void patientStarter() {
		patient.setId(patient_id);
		patient.setName(patient_name);
		patient.setSurname(patient_surname);
	}
	
	void patientDTOStarter() {
		patientDTO.setId(patient_id);
		patientDTO.setName(patient_name);
		patientDTO.setSurname(patient_surname);
	}
	
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
	
	void clinicStarter() {
		clinic.setId(1);
		clinic.setName("As Isa");
	}
	
	void medicalVisitStarter() {
		medicalVisit.setId(1);
		medicalVisit.setDate(null);
		medicalVisit.setRoom(room);
		medicalVisit.setTurn(Turn.T);
		medicalVisit.setDoctor(doctor);
	}
	
	void medicalVisitDTOStarter() {
		medicalVisitDTO.setId(1);
		medicalVisitDTO.setDate(null);
		medicalVisitDTO.setRoom_id(room.getId());
		medicalVisitDTO.setTurn(Turn.T);
		medicalVisitDTO.setDoctor_id(doctor.getId());
	}
	
	void appointmentStarter() {
		appointment.setId(id);
		appointment.setMedicalVisit(medicalVisit);
		appointment.setPatient(patient);
		appointments.add(appointment);
		appointmentPage = new PageImpl<>(appointments);
	}
	
	void appointmentDTOStarter() {
		appointmentDTO.setId(id);
		appointmentDTO.setMedicalVisit(medicalVisit.getId());
		appointmentDTO.setPatient(patient.getId());
	}
	
	void mockitos() throws NotFoundException{
		Mockito.when(appointmentDao.findAll(new PageRequest(0,10))).thenReturn(appointmentPage);
		Mockito.when(appointmentDao.findOne(1)).thenReturn(appointment);
		Mockito.when(mapper.map(appointment, AppointmentDTO.class)).thenReturn(
				new AppointmentDTO(appointment.getId(),appointment.getMedicalVisit(),appointment.getPatient()));
		Mockito.when(mvService.transform(mvService.findOne(1))).thenReturn(medicalVisit);
		Mockito.when(ptService.transform(ptService.findById(1))).thenReturn(patient);
		Mockito.when(appointmentDao.save(appointment)).thenReturn(appointment);
		Mockito.when(a.getPatient()).thenReturn(patient);
		Mockito.when(ptService.transform(patient)).thenReturn(patientDTO);
		Mockito.when(a.getMedicalVisit()).thenReturn(medicalVisit);
		Mockito.when(mvService.transform(medicalVisit)).thenReturn(medicalVisitDTO);
		Mockito.when(dService.transform(doctor)).thenReturn(doctorDTO);
	}
	
	@Before
	public void Initializer() throws NotFoundException{
		clinicStarter();
		roomStarter();
		medicalVisitStarter();
		medicalVisitDTOStarter();
		patientStarter();
		patientDTOStarter();
		doctorStarter();
		doctorDTOStarter();
		appointmentStarter();
		appointmentDTOStarter();
		mockitos();
	}
	
	@Test
	public void testFindAllFine()  throws NotFoundException{
		final List<AppointmentDTO> res = service.findAll(0,10);
		Assert.assertEquals(service.transform(appointment), res.get(0));
	}
	

	@Test(expected = NotFoundException.class)
	public void testFindAllWrong() throws NotFoundException{
		Mockito.when(appointmentDao.findAll(new PageRequest(0,10))).thenReturn(null);
		service.findAll(0,10);
	}
	
	@Test
	public void testFindByIdFine()  throws NotFoundException{
		final AppointmentDTO res = service.findById(1);
		Assert.assertEquals(appointment.getId(), res.getId());
		Assert.assertEquals(appointment.getMedicalVisit().getId(), res.getMedicalVisit());
		Assert.assertEquals(appointment.getPatient().getId(), res.getPatient());
	}
	
	@Test(expected = NotFoundException.class)
	public void testFindByIdWrong() throws NotFoundException{
		service.findById(90);
	}
	
	@Test
	public void testCreateFine() throws InvalidDataException, NotFoundException {
		final AppointmentDTO res = service.create(appointmentDTO);
		Assert.assertEquals(appointment.getId(), id);
		Assert.assertEquals(appointment.getMedicalVisit().getId(), res.getMedicalVisit());
		Assert.assertEquals(appointment.getPatient().getId(), res.getPatient());
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
	
	@Test(expected = InvalidDataException.class)
	public void testCreateWrong() throws InvalidDataException, NotFoundException{
		service.create(new AppointmentDTO());
	}	
	
	@Test
	public void testUpdateFine() throws NotFoundException {
		service.update(appointmentDTO);
	}
	
	@Test
	public void testDeleteFine() {
		service.delete(id);
	}
	
	@Test
	public void testGetPatientFine() throws NotFoundException {
		PatientDTO d = service.getPatient(1);
		Assert.assertEquals(patient.getId(), d.getId());
		Assert.assertEquals(patient.getName(), d.getName());
		Assert.assertEquals(patient.getSurname(), d.getSurname());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetPatientWrong() throws NotFoundException{
		service.getPatient(200);
	}
	
	@Test
	public void testGetMedicalVisitFine() throws NotFoundException {
		MedicalVisitDTO d = service.getMedicalVisit(1);
		Assert.assertEquals(medicalVisit.getId(), d.getId());
	}
	
	@Test(expected = NotFoundException.class)
	public void testGetGetMedicalVisittWrong() throws NotFoundException{
		service.getMedicalVisit(200);
	}
	
}
