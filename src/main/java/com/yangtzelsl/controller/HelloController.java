package com.yangtzelsl.controller;

import com.yangtzelsl.model.SysUser;
import com.yangtzelsl.security.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liusilin
 * @Date: 2021/4/11 09:43
 * @Description:
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

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

    @PostMapping("/login")
    public String login(@RequestBody SysUser sysUser, HttpServletRequest request){
        final UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }
}
