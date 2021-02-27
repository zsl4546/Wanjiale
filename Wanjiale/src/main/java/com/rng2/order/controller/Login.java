package com.rng2.order.controller;

import com.rng2.order.bean.Division;
import com.rng2.order.bean.User;
import com.rng2.order.service.impl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/15
 * Time: 21:42
 * Description: No Description
 */
@RestController
@RequestMapping("/z")
public class Login {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public User login(User user){
        System.out.println("=>"+user);
        System.out.println("1");
        User user1 = loginService.login(user);
        System.out.println(user1);
        return user1;
    }
    @GetMapping("/save/{id}/{account}/{tag}/{code}")
    public void save(HttpServletRequest request,
                     @PathVariable("id") String id,
                     @PathVariable("account") String account,
                     @PathVariable("tag") String tag,
                     @PathVariable("code") String code){
        HttpSession session = request.getSession();
        session.setAttribute("id", id);
        session.setAttribute("account", account);
        session.setAttribute("tag", tag);
        session.setAttribute("code", code);
    }

    @GetMapping("/load")
    public User load(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = new User();
//        System.out.println(session.getAttribute("id"));
        user.setId(Integer.parseInt((String) session.getAttribute("id")));
        user.setAccount((String) session.getAttribute("account"));
        user.setTag(Integer.parseInt((String) session.getAttribute("tag")));
        user.setCode(Integer.parseInt((String) session.getAttribute("code")));
        System.out.println(user);
        System.out.println(2);
        return user;
    }

    @GetMapping("/code/{id}")
    public Division findCode(@PathVariable("id") int id){
        return loginService.findCode(id);
    }
}
