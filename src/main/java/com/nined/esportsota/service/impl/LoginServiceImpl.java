package com.nined.esportsota.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.config.RedisKey;
import com.nined.esportsota.domain.Login;
import com.nined.esportsota.domain.Users;
import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.repository.UserRepository;
import com.nined.esportsota.rest.WXController;
import com.nined.esportsota.service.LoginService;
import com.nined.esportsota.service.RedisService;
import com.nined.esportsota.service.dto.UserDTO;
import com.nined.esportsota.service.mapper.UserMapper;
import com.nined.esportsota.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static Logger logger= LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public boolean code(String mobile){
        if (StringUtils.isEmpty(mobile)){
            throw new BadRequestException("参数异常");
        }
        String code= RandomStringUtils.random(4,"0123456789");
        //有效期为10分钟
        redisService.set(RedisKey.WX_VERIFY_MOBILE+mobile,code,10L, TimeUnit.MINUTES);
        return SmsUtil.sendCode(mobile,code);
    }

    @Override
    public Object wxUser(Login login){
        if (StringUtils.isEmpty(login)){
            throw new BadRequestException("参数异常");
        }
        if (StringUtils.isEmpty(login.getMobile())){
            throw new BadRequestException("参数异常");
        }
        return getUserInfo(login);
    }

    @Override
    public Object bindUser(Login login){
        if (StringUtils.isEmpty(login)){
            throw new BadRequestException("参数异常");
        }
        if (StringUtils.isEmpty(login.getMobile())||StringUtils.isEmpty(login.getCode())){
            throw new BadRequestException("参数异常");
        }
        //验证码判断
        Object verify=redisService.get(RedisKey.WX_VERIFY_MOBILE+login.getMobile());
        if (StringUtils.isEmpty(verify)||!login.getCode().equals(verify.toString())){
            throw new BadRequestException("验证码错误！");
        }

        return getUserInfo(login);
    }

    @Override
    public Object findByToken(String sessionId){
        if (StringUtils.isEmpty(sessionId)){
            throw new BadRequestException("参数异常");
        }
        Users user=userRepository.findBySessionId(sessionId);
        if (StringUtils.isEmpty(user)){
            throw new BadRequestException("未找到此用户");
        }
        UserDTO userDTO=userMapper.toDto(user);
        String token=TokenUtils.getToken(user.getMobile());
        redisService.set(RedisKey.WX_USER_TOKEN+userDTO.getId(),token);
        userDTO.setToken(token);
        userDTO.setOpenid("test111");
        return userDTO;
    }

    private UserDTO getUserInfo(Login login){
        logger.info("【登录参数：】"+login.toString());
        Users user=userRepository.findByMobile(login.getMobile());
        if (StringUtils.isEmpty(user)){
            Users newUser=new Users();
            newUser.setMobile(login.getMobile());
            newUser.setGender(login.getGender());
            newUser.setNickName(login.getNickName());
            newUser.setFaceImage(login.getFaceImage());
            newUser.setPassword("");
            newUser.setCreateTime(new Timestamp(new Date().getTime()));
            user =userRepository.save(newUser);

        }
        UserDTO userDTO=userMapper.toDto(user);
        String token=TokenUtils.getToken(user.getMobile());
        redisService.set(RedisKey.WX_USER_TOKEN+userDTO.getId(),token);
        userDTO.setToken(token);
        JSONObject object= WXUtil.getSessionKey(login.getWxCode());
        logger.info("【微信获取token返回参数：】"+object.toJSONString());
        userDTO.setOpenid(object.getString("openid"));
        return userDTO;
    }

}
