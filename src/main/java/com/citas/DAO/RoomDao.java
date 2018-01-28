package com.citas.DAO;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.citas.model.Room;

public interface RoomDao extends PagingAndSortingRepository<Room,Integer> {

}
