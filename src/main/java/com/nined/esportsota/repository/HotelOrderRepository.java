package com.nined.esportsota.repository;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.domain.HotelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelOrderRepository extends JpaRepository<HotelOrder,Integer>, JpaSpecificationExecutor<HotelOrder> {

    @Query
    List<HotelOrder> findByRoomTypeIdAndOrderId(Integer roomTypeId,String orderId);

    @Query(value = "select status,room_type_id,order_id,shop_id,book_in_date,book_out_date,pay_time,order_user_mobile,pay_method,create_time " +
            "from hotel_order where user_id=?1 " +
            "GROUP BY status,room_type_id,order_id,shop_id,book_in_date,book_out_date,pay_time,order_user_mobile,pay_method,create_time " +
            "limit ?2,?3",nativeQuery = true)
    List<JSONObject> findByGroup(String mobile,int skip,int size);

    @Query(value = "select order_id from (select order_id from hotel_order order by id desc) as t where instr(order_id,?1) > 0 limit 1",nativeQuery = true)
    String findByPrefix(String prefix);

    @Query(value = "select id from hotel_order where order_id=?1",nativeQuery = true)
    String findByNewNo(String newNo);

    @Query
    HotelOrder findByOrderId(String orderId);

    @Query(value = "select * from hotel_order where order_id=?1 and user_id=?2",nativeQuery = true)
    HotelOrder findByOrderIdAAndUserId(String orderId,Integer userId);
}
