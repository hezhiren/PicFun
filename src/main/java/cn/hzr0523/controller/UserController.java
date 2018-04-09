package cn.hzr0523.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * hezhi
 * 2018/1/23 11:36
 */
@Controller
@EnableAutoConfiguration
public class UserController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
