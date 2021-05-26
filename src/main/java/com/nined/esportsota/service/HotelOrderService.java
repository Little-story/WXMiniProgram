package com.nined.esportsota.service;

import com.nined.esportsota.domain.HotelOrder;
import com.nined.esportsota.service.criteria.HotelOrderQueryCriteria;
import com.nined.esportsota.service.dto.HotelOrderDTO;
import com.nined.esportsota.service.vo.HotelOrderVO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface HotelOrderService {

    /**
     * 分页查询
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(HotelOrderQueryCriteria criteria, Pageable pageable);

    /**
     * 小程序创建订单
     * @param hotelOrderVO
     * @return
     */
    HotelOrderDTO create(HotelOrderVO hotelOrderVO);

    /**
     * 根据订单号查找
     * @param orderId
     * @return
     */
    HotelOrder findByOrderId(String orderId);

    /**
     * 订单详情
     * @param orderId
     * @param userId
     * @return
     */
    HotelOrderDTO findByOrderIdAndUserId(String orderId,Integer userId);

    /**
     * 微信取消订单
     * @param orderId
     * @param userId
     * @return
     */
    boolean cancelOrder(String orderId,Integer userId);

    /**
     * 内部取消订单
     * @param hotelOrder
     */
    void systemCancelOrder(HotelOrder hotelOrder);
}
