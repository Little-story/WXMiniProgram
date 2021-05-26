package com.nined.esportsota.repository;

import com.nined.esportsota.domain.HotelOrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelOrderUserRepository extends JpaRepository<HotelOrderUser,Integer>, JpaSpecificationExecutor<HotelOrderUser> {

    @Query
    HotelOrderUser findByNameAndMobile(String name,String mobile);

    @Query(value = "select code from (select code from hotel_order_user order by id desc) as t where instr(code,?1) > 0 limit 1",nativeQuery = true)
    String findByPrefix(String prefix);

    @Query(value = "select id from hotel_order_user where code=?1",nativeQuery = true)
    String findByNewNo(String newNo);
}
