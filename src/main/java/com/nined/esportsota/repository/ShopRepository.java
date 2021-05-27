package com.nined.esportsota.repository;

import com.nined.esportsota.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer>, JpaSpecificationExecutor<Shop> {

    /**
     * 更新酒店经纬度
     * @param shopId    酒店id
     * @param lng   经度
     * @param lat   纬度
     */
    @Query(value = "update shop set longitude=?2,latitude=?3 where id=?1",nativeQuery = true)
    @Transactional
    @Modifying
    void updateLocation(Integer shopId,String lng,String lat);
}
