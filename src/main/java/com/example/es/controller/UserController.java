package com.example.es.controller;

import com.example.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("/getIndex")
    public String getUserIndex() throws IOException {
        return userService.getUserIndex();
    }

    @ResponseBody
    @RequestMapping("/addIndex")
    public String addUserIndex() throws IOException {
        userService.addIndex();
        return "ok";
    }
}
