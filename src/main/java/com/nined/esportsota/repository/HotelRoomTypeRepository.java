package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelRoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomTypeRepository extends JpaRepository<HotelRoomType,Integer>, JpaSpecificationExecutor<HotelRoomType> {

    @Query(value = "select *,min(unit_price) from hotel_room_type where shop_id=?1 and status=1",nativeQuery = true)
    HotelRoomType findOne(Integer shopId);
}
