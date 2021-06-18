package com.nined.esportsota.service.impl;

import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.repository.HotelOrderRepository;
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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelRoomServiceImpl implements HotelRoomService {

    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelOrderRepository hotelOrderRepository;

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public Object queryAll(HotelRoomQueryCriteria criteria, Pageable pageable) {
        Page<HotelRoom> page=hotelRoomRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        Map<String,Object> map= PageUtil.toPage(page.map(hotelRoomMapper::toDto));
        return map;
    }

    @Override
    public List<HotelRoom> roomNum(HotelRoomQueryCriteria criteria){
        List<HotelRoom> roomList=new ArrayList<>();
        criteria.setStatus(1);
        List<Integer> roomStatus=new ArrayList<>();
        roomStatus.add(99);
        criteria.setRoomStatus(roomStatus);
        List<HotelRoom> list = hotelRoomRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)));
        for (HotelRoom hotelRoom:list){
            //查询房间是否在预定时间内
            String roomId="%"+hotelRoom.getId()+"%";
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String startTime=sdf.format(criteria.getBookInDate());
            String endTime=sdf.format(criteria.getBookOutDate());
            int result=hotelOrderRepository.countByRoomOrder(roomId,startTime,endTime);
            if (result==0){
                roomList.add(hotelRoom);
            }
        }
        return roomList;
    }
}
