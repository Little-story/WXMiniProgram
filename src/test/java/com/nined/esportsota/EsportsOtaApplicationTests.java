package com.nined.esportsota;

import com.nined.esportsota.domain.HotelOrder;
import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.domain.HotelRoomType;
import com.nined.esportsota.domain.Shop;
import com.nined.esportsota.repository.HotelOrderRepository;
import com.nined.esportsota.repository.HotelRoomRepository;
import com.nined.esportsota.repository.HotelRoomTypeRepository;
import com.nined.esportsota.repository.ShopRepository;
import com.nined.esportsota.service.HotelRoomService;
import com.nined.esportsota.service.criteria.HotelRoomQueryCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EsportsOtaApplicationTests {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelOrderRepository hotelOrderRepository;

    @Autowired
    private HotelRoomService hotelRoomService;

    @Test
    void contextLoads(){

        /*HotelRoomQueryCriteria criteria=new HotelRoomQueryCriteria();
        criteria.setRoomTypeId(1);
        criteria.setBookInDate(1623981961000L);
        criteria.setBookOutDate(1624068361000L);
        List<HotelRoom> roomList=hotelRoomService.roomNum(criteria);
        System.out.println(roomList.size());*/

        //清理房型
        /*List<HotelRoomType> hotelRoomTypeList=hotelRoomTypeRepository.findAll();
        for (HotelRoomType roomType:hotelRoomTypeList){
            Shop shop=shopRepository.findById(roomType.getShopId()).orElse(new Shop());
            if (StringUtils.isEmpty(shop)){
                hotelRoomTypeRepository.delete(roomType);
            }
        }*/

        //清理房间
        /*List<HotelRoom> hotelRoomList=hotelRoomRepository.findAll();
        for (HotelRoom hotelRoom:hotelRoomList){
            Shop shop=shopRepository.findById(hotelRoom.getShopId()).orElse(new Shop());
            HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(hotelRoom.getRoomTypeId()).orElse(new HotelRoomType());
            if (StringUtils.isEmpty(shop.getId())||StringUtils.isEmpty(hotelRoomType.getId())){
                hotelRoomRepository.delete(hotelRoom);
            }
        }*/

        //清理订单
        /*List<HotelOrder> hotelOrderList=hotelOrderRepository.findAll();
        for (HotelOrder hotelOrder:hotelOrderList){
            Shop shop=shopRepository.findById(hotelOrder.getShopId()).orElse(new Shop());
            HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(hotelOrder.getRoomTypeId()).orElse(new HotelRoomType());
            if (StringUtils.isEmpty(shop.getId())||StringUtils.isEmpty(hotelRoomType.getId())){
                hotelOrderRepository.delete(hotelOrder);
            }
        }*/
    }

}
