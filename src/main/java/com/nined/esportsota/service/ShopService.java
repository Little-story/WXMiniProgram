package com.nined.esportsota.service;

import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import com.nined.esportsota.service.dto.ShopDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {

    /**
     * 分页查询
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(ShopQueryCriteria criteria, Pageable pageable);

    /**
     * 酒店详情
     * @param id
     * @return
     */
    ShopDTO findById(Integer id);
}
