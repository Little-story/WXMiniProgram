package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelMessageRepository extends JpaRepository<HotelMessage,Integer>, JpaSpecificationExecutor<HotelMessage> {

}
