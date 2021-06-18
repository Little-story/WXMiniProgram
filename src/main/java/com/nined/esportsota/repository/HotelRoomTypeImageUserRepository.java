package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelRoomType;
import com.nined.esportsota.domain.HotelRoomTypeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomTypeImageUserRepository extends JpaRepository<HotelRoomTypeImage,Integer>, JpaSpecificationExecutor<HotelRoomTypeImage> {

    @Query(value = "select file_name from hotel_room_type_image where room_type_id=?1 and type_id=1",nativeQuery = true)
    List<String> findByRoomTypeId(Integer roomTypeId);
}
