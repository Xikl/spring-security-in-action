package com.ximo.spring.security.sdk.demo.controller;

import com.ximo.spring.security.sdk.core.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱文赵
 * @date 2018/6/22
 * @description
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public ResultVO helloWord() {
        Map<String, String> map = new HashMap<>(1);
        map.put("key", "你好");
        return ResultVO.success(map);
    }



}
