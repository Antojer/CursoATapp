package com.citas.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.citas.model.Patient;

public interface PatientDao extends PagingAndSortingRepository<Patient,Integer>{

}
