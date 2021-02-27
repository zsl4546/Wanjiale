package com.editor.account.service.impl;

import com.editor.account.bean.User;
import com.editor.account.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 19:57
 * Description: No Description
 */
@Service
public class LoginImpl implements com.editor.account.service.Login {
    @Autowired
    private LoginRepository loginRepository;

    public User findUser(User user){
        return loginRepository.findUser(user);
    }

    @Override
    public User findUserByAccount(String account) {
        return loginRepository.findUserByAccount(account);
    }

    @Override
    public User checkUser(String account) {
        return loginRepository.checkUser(account);
    }

    @Override
    public List<User> findAllUser(User user) {
        return loginRepository.findAllUser(user);
    }

    @Override
    public void changeStatus(User user) {
        loginRepository.changeStatus(user);
    }

    @Override
    public void deleteUser(String account) {
        loginRepository.deleteUser(account);
    }

    @Override
    public void addUser(User user) {
        loginRepository.addUser(user);
    }
}
