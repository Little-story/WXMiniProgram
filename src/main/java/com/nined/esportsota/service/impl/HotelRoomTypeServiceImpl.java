package com.nined.esportsota.service.impl;

import com.nined.esportsota.domain.HotelRoomComputer;
import com.nined.esportsota.domain.HotelRoomType;
import com.nined.esportsota.domain.Shop;
import com.nined.esportsota.repository.HotelRoomComputerRepository;
import com.nined.esportsota.repository.HotelRoomTypeImageUserRepository;
import com.nined.esportsota.repository.HotelRoomTypeRepository;
import com.nined.esportsota.repository.ShopRepository;
import com.nined.esportsota.service.HotelRoomTypeService;
import com.nined.esportsota.service.criteria.HotelRoomTypeQueryCriteria;
import com.nined.esportsota.service.dto.HotelRoomDTO;
import com.nined.esportsota.service.dto.HotelRoomTypeDTO;
import com.nined.esportsota.service.mapper.HotelRoomTypeMapper;
import com.nined.esportsota.utils.PageUtil;
import com.nined.esportsota.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HotelRoomTypeServiceImpl implements HotelRoomTypeService {

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private HotelRoomComputerRepository hotelRoomComputerRepository;
    @Autowired
    private HotelRoomTypeImageUserRepository hotelRoomTypeImageUserRepository;

    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;

    @Override
    public Object queryAll(HotelRoomTypeQueryCriteria criteria, Pageable pageable) {
        criteria.setStatus(1);
        Page<HotelRoomType> page=hotelRoomTypeRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        List<HotelRoomType> list=page.getContent();
        if (!StringUtils.isEmpty(list)&&list.size()>0){
            for (HotelRoomType hotelRoomType:page.getContent()){
                addInfo(hotelRoomType);
            }
        }

        Map<String,Object> map= PageUtil.toPage(page.map(hotelRoomTypeMapper::toDto));
        return map;
    }

    @Override
    public HotelRoomTypeDTO detail(Integer roomTypeId){
        HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(roomTypeId).orElse(new HotelRoomType());
        if (!StringUtils.isEmpty(hotelRoomType.getId())){
            addInfo(hotelRoomType);
        }
        return hotelRoomTypeMapper.toDto(hotelRoomType);
    }

    /**
     * 补充房型信息
     * @return
     */
    private void addInfo(HotelRoomType hotelRoomType){
        //手机号信息
        Shop shop=shopRepository.findById(hotelRoomType.getShopId()).get();
        hotelRoomType.setShopName(shop.getName());
        hotelRoomType.setMobile(shop.getMobile());
        //电脑信息
        if (!StringUtils.isEmpty(hotelRoomType.getComputerId())){
            hotelRoomType.setHotelRoomComputer(hotelRoomComputerRepository.findById(hotelRoomType.getComputerId()).orElse(new HotelRoomComputer()));
        }else {
            hotelRoomType.setHotelRoomComputer(new HotelRoomComputer());
        }
        //补充房型图片
        List<String> imageList=hotelRoomTypeImageUserRepository.findByRoomTypeId(hotelRoomType.getId());
        hotelRoomType.setImageList(imageList);
    }
}
