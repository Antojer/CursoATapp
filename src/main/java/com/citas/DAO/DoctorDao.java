package com.citas.DAO;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.citas.DTO.Doctor.DoctorAndTotalMedicalVisitDTO;
import com.citas.DTO.Doctor.DoctorTopDTO;
import com.citas.model.Doctor;

@Repository
public interface DoctorDao extends PagingAndSortingRepository<Doctor,Integer>{
	@Query(value = "select new com.citas.DTO.Doctor.DoctorTopDTO(d.id, d.name,d.surname,d.externalApiId,"
			+ " count(b.doctor)) "
			+ "from Doctor d INNER JOIN d.medicalvisits b INNER JOIN b.appointments c"
			+ " group by d.id"
			+ " order by count(distinct c.patient) DESC")
	List<DoctorTopDTO> findTopNDoctors(Pageable n);
	
	@Query(value = "select new com.citas.DTO.Doctor.DoctorAndTotalMedicalVisitDTO(d.id, count(b.doctor),"
			+ " d.name, d.surname, d.externalApiId)"
			+ "from Doctor d INNER JOIN d.medicalvisits b where b.date BETWEEN :dIni and :dFin "
			+ "group by d.id")
	List<DoctorAndTotalMedicalVisitDTO> getDoctorByMedicalVisitDate(@Param(value = "dIni") Date dIni,@Param(value = "dFin") Date dFin);

}
