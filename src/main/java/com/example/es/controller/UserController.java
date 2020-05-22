package com.example.es.controller;

<<<<<<< HEAD
import com.example.es.entity.User;
=======
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
import com.example.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
<<<<<<< HEAD
import java.util.Date;
import java.util.UUID;
=======
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45

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
<<<<<<< HEAD
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
=======
        userService.addIndex();
        return "ok";
    }
>>>>>>> 30270083436a9d909ab159b2324f16a352c83d45
}
