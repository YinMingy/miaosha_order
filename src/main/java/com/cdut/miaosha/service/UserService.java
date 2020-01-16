package com.cdut.miaosha.service;

import com.cdut.miaosha.dao.UserDao;
import com.cdut.miaosha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：yinmy
 * @date ：Created on 2020/1/13 17:01
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }


    @Transactional
    public boolean tx() {
        User user1 = new User();
        user1.setId(2);
        user1.setName("2222");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setName("11111");
        userDao.insert(user2);
        return true;
    }
}
