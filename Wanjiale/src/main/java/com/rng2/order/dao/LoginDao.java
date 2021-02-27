package com.rng2.order.dao;

import com.rng2.order.bean.Division;
import com.rng2.order.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/15
 * Time: 21:21
 * Description: No Description
 */
@Mapper
public interface LoginDao {
    public User login(User user);
    public Division findCode(int code);
}
