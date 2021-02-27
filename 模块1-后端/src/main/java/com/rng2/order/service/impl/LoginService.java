package com.rng2.order.service.impl;

import com.rng2.order.bean.Division;
import com.rng2.order.bean.User;
import com.rng2.order.dao.LoginDao;
import com.rng2.order.service.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/15
 * Time: 21:31
 * Description: No Description
 */
@Service
public class LoginService implements Login {
    @Autowired
    private LoginDao loginDao;

    @Override
    public User login(User user) {
        return loginDao.login(user);
    }

    @Override
    public Division findCode(int code) {
        return loginDao.findCode(code);
    }
}
