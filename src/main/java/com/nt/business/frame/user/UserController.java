package com.nt.business.frame.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Xu
 * @create: 2019-06-11 23:33
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/login1/{username}/{password}")
    public User login(@PathVariable String username, @PathVariable String password) {

        System.out.println(username);
        System.out.println(password);

        User user = new User();
        user.setAddress("sss");
        user.setEmail("cccc");
        return user;
    }

    @RequestMapping("/login2")
    public User login2(String username, String password) {

        System.out.println(username);
        System.out.println(password);

        User user = new User();
        user.setAddress("sss");
        user.setEmail("cccc2");
        return user;
    }

    @RequestMapping("/login3")
    public User login3(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {

        System.out.println(username);
        System.out.println(password);

        User user = new User();
        user.setAddress("sss");
        user.setEmail("cccc3");
        return user;
    }

}
