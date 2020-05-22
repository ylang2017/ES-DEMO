package com.example.es.controller;

import com.example.es.entity.User;
import com.example.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

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
        userService.addUserIndex();
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public String addUser() throws IOException {
        User user = new User(UUID.randomUUID().toString(), "小明", 20,
            "富、民、和、文，自、平、公、法",
            "富、民、和、文，自、平、公、法",
            new Date(), false);

        userService.saveUser(user);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public User getUser() throws IOException {
        User user = new User(UUID.randomUUID().toString(), "小明", 20,
                "富、民、和、文，自、平、公、法",
                "富、民、和、文，自、平、公、法",
                new Date(), false);

        return userService.getUser("678YOnIBjdswrGGdxQuN");
    }
}
