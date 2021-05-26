package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom,Integer>, JpaSpecificationExecutor<HotelRoom> {

    @Query(value = "select * from hotel_room where room_status=1 and status=1 and room_type_id=?1 limit ?2",nativeQuery = true)
    List<HotelRoom> findByRoomTypeId(Integer roomTypeId,Integer limit);


    @Query(value = "select count(*) from hotel_room where shop_id=?1",nativeQuery = true)
    int countByShopId(Integer shopId);
}
