package com.nined.esportsota.rest;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.domain.Login;
import com.nined.esportsota.service.LoginService;
import com.nined.esportsota.service.ShopService;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;


    @GetMapping(value = "/code")
    public ResponseEntity<Object> code(String mobile) {
        Map map=new HashMap();
        map.put("data",loginService.code(mobile));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/wxUser")
    public ResponseEntity<Object> wxUser( @RequestBody Login login) {
        Map map=new HashMap();
        map.put("data",loginService.wxUser(login));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/bindUser")
    public ResponseEntity<Object> bindUser(@RequestBody Login login) {
        Map map=new HashMap();
        map.put("data",loginService.bindUser(login));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/findByToken")
    public ResponseEntity<Object> findByToken(String sessionId) {
        Map map=new HashMap();
        map.put("data", loginService.findByToken(sessionId));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}