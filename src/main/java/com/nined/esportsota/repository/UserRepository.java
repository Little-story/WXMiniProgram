package com.nined.esportsota.repository;

import com.nined.esportsota.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer>, JpaSpecificationExecutor<Users> {

    @Query(value = "select * from user where mobile=?1",nativeQuery = true)
    Users findByMobile(String mobile);

    @Query(value = "select * from user where session_id=?1",nativeQuery = true)
    Users findBySessionId(String sessionId);
}
