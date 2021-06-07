package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface HotelOrderRepository extends JpaRepository<HotelOrder,Integer>, JpaSpecificationExecutor<HotelOrder> {


    @Query(value = "select order_id from (select order_id from hotel_order order by id desc) as t where instr(order_id,?1) > 0 limit 1",nativeQuery = true)
    String findByPrefix(String prefix);

    @Query(value = "select id from hotel_order where order_id=?1",nativeQuery = true)
    String findByNewNo(String newNo);

    @Query
    HotelOrder findByOrderId(String orderId);

    @Query(value = "select * from hotel_order where order_id=?1 and user_id=?2",nativeQuery = true)
    HotelOrder findByOrderIdAAndUserId(String orderId,Integer userId);

    /**
     * 时间段内已预订的房间
     * @return
     */
    @Query(value = "select count(*) from hotel_order where status=2 and (room_list like ?1 or room_id like ?1) and" +
            " ((book_in_date between ?2 and ?3) or (book_out_date between ?2 and ?3))",nativeQuery = true)
    int countByRoomOrder(String roomId, Timestamp bookInDate,Timestamp bookOutDate);
}
