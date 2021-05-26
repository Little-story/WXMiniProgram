package com.nined.esportsota.service;

import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.service.criteria.HotelRoomQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelRoomService {

    /**
     * 分页查询
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(HotelRoomQueryCriteria criteria, Pageable pageable);

    /**
     * 预定房间
     * @param roomTypeId
     * @param num
     * @return
     */
    List<HotelRoom> orderRoom(Integer roomTypeId,Integer num);
}
