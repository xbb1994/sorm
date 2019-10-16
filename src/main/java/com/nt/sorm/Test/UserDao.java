package com.nt.sorm.Test;

import com.nt.sorm.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Xu
 * @create: 2019-05-10 11:06
 **/
@Repository
public class UserDao extends BaseDao<User> {


    @Override
    public int insert(User user) {
        return super.insert(user);
    }

    @Override
    protected int update(User user) {
        return super.update(user);
    }
}
