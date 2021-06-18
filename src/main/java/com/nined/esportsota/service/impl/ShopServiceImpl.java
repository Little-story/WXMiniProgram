package com.nined.esportsota.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.domain.HotelRoomType;
import com.nined.esportsota.domain.Shop;
import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.repository.HotelRoomRepository;
import com.nined.esportsota.repository.HotelRoomTypeRepository;
import com.nined.esportsota.repository.ShopRepository;
import com.nined.esportsota.service.ShopService;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import com.nined.esportsota.service.dto.ShopDTO;
import com.nined.esportsota.service.mapper.ShopMapper;
import com.nined.esportsota.utils.BaiduMapUtil;
import com.nined.esportsota.utils.PageUtil;
import com.nined.esportsota.utils.QueryHelp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Object queryAll(ShopQueryCriteria criteria, Pageable pageable) {
        criteria.setStatus(1);
        criteria.setTypeId(2);
        String name="'%%'";
        if (!StringUtils.isEmpty(criteria.getName())){
            name="'%"+criteria.getName()+"%'";
        }

        List<Shop> list=new ArrayList<>();
        if (!StringUtils.isEmpty(criteria.getLongitude())&&!StringUtils.isEmpty(criteria.getLatitude())){
            //根据经纬度排序
            String sql= MessageFormat.format("select * from shop where name like {0} and status=1 and type_id=2 order by ABS({1}-longitude) ASC,ABS({2}-latitude) ASC limit {3},{4}",
                    name,criteria.getLongitude(),criteria.getLatitude(),pageable.getPageNumber()* pageable.getPageSize(),pageable.getPageSize());
            list=entityManager.createNativeQuery(sql,Shop.class).getResultList();
        }else {
            Page<Shop> page=shopRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
            list=page.getContent();
        }
        //补充酒店首页信息
        for (Shop shop:list){
            addInfo(shop);
        }

        Page<Shop> page=new PageImpl<>(list,pageable,shopRepository.count());
        Map<String,Object> map= PageUtil.toPage(page.map(shopMapper::toDto));
        return map;
    }

    @Override
    public ShopDTO findById(Integer id){
        Shop shop=shopRepository.findById(id).orElse(new Shop());
        addInfo(shop);
        return shopMapper.toDto(shop);
    }

    //补充酒店首页信息
    private Shop addInfo(Shop shop){
        if (StringUtils.isEmpty(shop.getLongitude())||StringUtils.isEmpty(shop.getLatitude())){
            //补充经纬度信息
            if (!StringUtils.isEmpty(shop.getCity())&&!StringUtils.isEmpty(shop.getDistrict())&&!StringUtils.isEmpty(shop.getAddress())){
                String address=shop.getCity()+shop.getDistrict()+shop.getAddress();
                JSONObject object=BaiduMapUtil.getLocation(address);
                shopRepository.updateLocation(shop.getId(),object.getString("lng"),object.getString("lat"));
            }
        }
        HotelRoomType hotelRoomType=hotelRoomTypeRepository.findOne(shop.getId());
        int roomNum=hotelRoomRepository.countByShopId(shop.getId());
        if (!StringUtils.isEmpty(hotelRoomType)){
            shop.setAmount(hotelRoomType.getUnitPrice());
            shop.setRoomTypeName(hotelRoomType.getName());
            shop.setBedTypeName(hotelRoomType.getBedType());
            shop.setRoomNum(roomNum);
            shop.setOriginalPrice(hotelRoomType.getOriginalPrice());
        }
        return shop;
    }
}
