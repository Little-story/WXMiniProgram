package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelRoomComputer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomComputerRepository extends JpaRepository<HotelRoomComputer,Integer>, JpaSpecificationExecutor<HotelRoomComputer> {

}
