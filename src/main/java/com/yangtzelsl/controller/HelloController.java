package com.yangtzelsl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    @GetMapping("/hello")
    public ModelAndView hello() {
        log.info("测试日志输出~~~~~~~~~");
        //return "hello springboot 2!";
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/hello.html");
        return mv;

    }
}
