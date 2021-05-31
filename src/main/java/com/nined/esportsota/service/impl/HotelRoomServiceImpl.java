package com.nined.esportsota.service.impl;

import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.repository.HotelRoomRepository;
import com.nined.esportsota.service.HotelRoomService;
import com.nined.esportsota.service.HotelRoomTypeService;
import com.nined.esportsota.service.criteria.HotelRoomQueryCriteria;
import com.nined.esportsota.service.criteria.HotelRoomTypeQueryCriteria;
import com.nined.esportsota.service.mapper.HotelRoomMapper;
import com.nined.esportsota.utils.PageUtil;
import com.nined.esportsota.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public Object queryAll(HotelRoomQueryCriteria criteria, Pageable pageable) {
        Page<HotelRoom> page=hotelRoomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        Map<String,Object> map= PageUtil.toPage(page.map(hotelRoomMapper::toDto));
        return map;
    }

    @Override
    public int roomNum(Integer roomTypeId){
        return hotelRoomRepository.countByRoomTypeId(roomTypeId);
    }
}
