package com.editor.account.service;

import com.editor.account.bean.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 19:56
 * Description: No Description
 */
public interface Login {
    public User findUser(User user);
    public User findUserByAccount(String account);
    public User checkUser(String account);
    public List<User> findAllUser(User user);
    public void changeStatus(User user);
    public void deleteUser(String account);
    public void addUser(User user);
}
