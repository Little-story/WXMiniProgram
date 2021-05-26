package com.nined.esportsota.repository;

import com.nined.esportsota.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer>, JpaSpecificationExecutor<Shop> {


}
