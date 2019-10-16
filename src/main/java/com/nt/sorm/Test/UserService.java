package com.nt.sorm.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Xu
 * @create: 2019-05-27 15:35
 **/
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public  void save(User user){
        userDao.insert(user);
    }

}
