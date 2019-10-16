package com.nt.sorm.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Xu
 * @create: 2019-05-10 01:55
 **/
@RestController
public class test {


    @Autowired
    UserService userService;

    @RequestMapping("/test")
    public  void test1(String[] args) {

        User user = new User();
        user.setRowGuid("00000022");
        user.setSalary(112);
        user.setAge(2332);
        user.setName("xxx2");
        userService.save(user);


    }
}
