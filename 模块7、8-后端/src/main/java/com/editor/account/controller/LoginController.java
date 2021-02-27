package com.editor.account.controller;

import com.editor.account.bean.Dealer;
import com.editor.account.bean.SessionInfo;
import com.editor.account.bean.User;
import com.editor.account.service.impl.DealerServiceImpl;
import com.editor.account.service.impl.LoginImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 19:55
 * Description: No Description
 */
@RestController
@RequestMapping("/login")
public class LoginController { // 没有特别说明，都是与运营人员相关的操作
    @Autowired
    private LoginImpl login;
    @Autowired
    private DealerServiceImpl dealerImpl;

    @PostMapping("/user") //登录,由于运营人员，注意运营人员和经销商的账号分别在两个表
    public User Login(User user){ //这里采用的是用实体类传参，会就前端的参数自动解析成User对象
        System.out.println(user.getAccount()+'-'+user.getPassword());
        return login.findUser(user); //如果发现了有相应的账户，返回的值不为空否则为空
    }

    @PostMapping("/dealer") //
    public Dealer dealerm(Dealer dealer){
        return dealerImpl.findDealer(dealer);
    }

    @GetMapping("/session1") //用于运营人员登录的session
    public void session1(HttpServletRequest request, User user){ //登录成功的话就把user存放在session里面，方便之后确定用户身份
        HttpSession session = request.getSession();
        System.out.println(user.getAccount() + '0');
        session.setAttribute("identity", "1");
        session.setAttribute("account", user.getAccount());
        session.setAttribute("admin", String.valueOf(user.getAdmin()));
        System.out.println(session.getAttribute("admin"));
    }

    @GetMapping("/session2/{identity}") //用于总经销商和子经销商登录的session，identity标识身份
    public void session2(HttpServletRequest request, Dealer dealer, @PathVariable("identity") String identity){
        HttpSession session = request.getSession();
        System.out.println(dealer.getAccount() + "0");
        session.setAttribute("identity", identity);
        session.setAttribute("account", dealer.getAccount());
        session.setAttribute("admin",  "-1"); //这个没用
    }

    @GetMapping("/load") //得到之前保存的session
    public SessionInfo load(HttpServletRequest request){ //跳转页面后将请求这个方法以确定用户身份
        HttpSession session = request.getSession();
        SessionInfo sessionInfo1 = new SessionInfo();

        sessionInfo1.setAccount((String) session.getAttribute("account"));
        sessionInfo1.setAdmin((String) session.getAttribute("admin"));
        sessionInfo1.setIdentity((String) session.getAttribute("identity"));

        return sessionInfo1; //返回给前端
    }

    @GetMapping("/findAllUser") // 通过user实现查询功能，如果user里面有account，就筛选account，有status就筛选status...
    public List<User> findAllUser(User user){
        return login.findAllUser(user);
    }

    @GetMapping("/account/{account}") // 通过account得到对应的User
    public User findUserByAccount(@PathVariable("account") String account){
        System.out.println(account);
        return login.findUserByAccount(account);
    }

    @GetMapping("/changeStatus") // 改变运营人员账号状态
    public void changeStatus(User user){
        login.changeStatus(user);
    }

    @PostMapping("/manyStatus/{status}") //批量更改运营人员账号的状态
    public void manyStatus(@RequestBody List<User> userList, @PathVariable("status") String status){
        for (User user: userList){
            user.setStatus(status);
            login.changeStatus(user);
        }
    }

    @GetMapping("/deleteUser") //删除单个账户
    public void deleteUser(String account){
        login.deleteUser(account);
    }

    @PostMapping("/manyDelete") //批量删除账户
    public void manyDelete(@RequestBody List<User> userList){
        for (User user:userList){
            login.deleteUser(user.getAccount());
        }
    }

    @PostMapping("/addUser") //添加账号
    public int addUser(@RequestBody User user){
        System.out.println('5');
        System.out.println(user);
        if (login.checkUser(user.getAccount()) != null){
            return 0;
        }
        login.addUser(user);
        return 1;
    }

}
