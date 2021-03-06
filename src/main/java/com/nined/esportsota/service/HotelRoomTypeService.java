package com.nined.esportsota.service;

import com.nined.esportsota.service.criteria.HotelRoomTypeQueryCriteria;
import com.nined.esportsota.service.dto.HotelRoomDTO;
import com.nined.esportsota.service.dto.HotelRoomTypeDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface HotelRoomTypeService {

    /**
     * 分页查询
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(HotelRoomTypeQueryCriteria criteria, Pageable pageable);

    /**
     * 房间详情
     * @param criteria
     * @return
     */
    HotelRoomTypeDTO detail(Integer roomTypeId);
}
