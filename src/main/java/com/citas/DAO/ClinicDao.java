package com.citas.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.citas.model.Clinic;

public interface ClinicDao extends PagingAndSortingRepository<Clinic,Integer>{

}
