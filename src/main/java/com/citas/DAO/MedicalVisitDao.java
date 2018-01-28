package com.citas.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.citas.model.MedicalVisit;

@Repository
public interface MedicalVisitDao extends PagingAndSortingRepository<MedicalVisit,Integer>{
	

}
