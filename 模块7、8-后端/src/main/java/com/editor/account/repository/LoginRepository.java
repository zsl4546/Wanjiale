package com.editor.account.repository;

import com.editor.account.bean.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 19:39
 * Description: No Description
 */
@Mapper
public interface LoginRepository {
    public User findUser(User user);
    public User findUserByAccount(String account);
    public User checkUser(String account);
    public List<User> findAllUser(User user);
    public void changeStatus(User user);
    public void deleteUser(String account);
    public void addUser(User user);
}
