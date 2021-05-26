package com.nined.esportsota.service;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.domain.Login;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import com.nined.esportsota.service.dto.ShopDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    /**
     * 获取登录验证码
     * @param mobile
     * @return
     */
    boolean code(String mobile);

    /**
     * 注册
     * @param login
     * @return
     */
    Object wxUser(Login login);

    /**
     * 微信用户登录
     * @param login
     * @return
     */
    Object bindUser(Login login);
}
