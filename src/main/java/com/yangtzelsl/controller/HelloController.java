package com.yangtzelsl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liusilin
 * @Date: 2021/4/11 09:43
 * @Description:
 */
@Slf4j
@RestController
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        log.info("测试日志输出~~~~~~~~~");
        return "hello springboot 2!";
    }
}
