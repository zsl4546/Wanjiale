package com.rng2.order.service;

import com.rng2.order.bean.Division;
import com.rng2.order.bean.User;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/15
 * Time: 21:23
 * Description: No Description
 */
public interface Login {
    public User login(User user);
    public Division findCode(int code);
}
