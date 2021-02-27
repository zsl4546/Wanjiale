package com.editor.account.controller;

import com.editor.account.bean.CDRelationship;
import com.editor.account.bean.Client;
import com.editor.account.bean.Dealer;
import com.editor.account.bean.User;
import com.editor.account.service.impl.DealerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/6
 * Time: 20:11
 * Description: No Description
 */
@RestController
@RequestMapping("/dealer")
public class DealerController {
    @Autowired
    private DealerServiceImpl dealerService;

    @GetMapping("/findClient") //通过account查询这个account对应的Client
    public List<Client> findClient(String account){
        return dealerService.findClient(account);
    }

    @GetMapping("/findAllClient") //查询出所有的CLient
    public List<Client> findAllClient(){
        return dealerService.findAllClient();
    }

    @GetMapping("/findClientChild") //查询出account和所有account子账户的Client，要注意这个对应的SQL语句有点复杂
    public List<Client> findClientChild(String account){
        return dealerService.findClientChild(account);
    }

    @PostMapping("/changePass/{oldPass}") //用于修改密码， dealer包含了account和newPass， 先检查account和oldPass，再更新密码
    public int changePass(Dealer dealer, @PathVariable("oldPass") String oldPass){
        Dealer check = new Dealer();
        check.setAccount(dealer.getAccount());
        check.setPassword(oldPass);
        System.out.println(check);
        System.out.println(dealer);
        if (dealerService.findDealer(check) != null){
            dealerService.changePass(dealer);
            System.out.println(1);
            return 1; //成功修改密码
        }else {
            System.out.println(0);
            return 0; //账户的密码错误
        }
    }

    @GetMapping("/findDealers") //用于实现筛选功能，有account就筛选account，有phone就筛选phone...
    public List<Dealer> findDealers(Dealer dealer){
        System.out.println(dealer);
        return dealerService.findDealers(dealer);
    }

    @GetMapping("/changeStatus") //改变status，用于启用/禁用
    public void changeStatus(Dealer dealer){
        dealerService.changeStatus(dealer);
    }

    @GetMapping("/deleteDealer") //删除account账号
    public void deleteDealer(String account){
        deleteDealer(account);
    }

    @PostMapping("/addDealer") //添加账号，要先验证数据库表里有没有要添加的account
    public int addDealer(@RequestBody Dealer dealer){
        System.out.println('5');
        System.out.println(dealer);
        if (dealerService.checkDealer(dealer.getAccount()) != null){
            return 0;
        }
        dealerService.addDealer(dealer);
        return 1;
    }

    @PostMapping("/manyDelete") //用于多选，批量删除
    public void manyDelete(@RequestBody List<Dealer> userList){
        for (Dealer user:userList){
            dealerService.deleteDealer(user.getAccount());
        }
    }

    @GetMapping("/changePassChild") //修改子账号的密码
    public void changePassChild(Dealer dealer){
        dealerService.changePass(dealer);
    }


    @PostMapping("/autho/{account}") //修改账号的关联客户，先删除与account关联的客户，再重新插入新的数据
    public void autho(@RequestBody List<String> checkedClient, @PathVariable("account") String account){
        System.out.println(checkedClient);
        System.out.println(account);
        List<CDRelationship> cdRelationships = new ArrayList<>();
        for (String s:checkedClient){
            cdRelationships.add(new CDRelationship(s, account));
        }
        dealerService.deleteRelationship(account);
        dealerService.addRelationship(cdRelationships);
    }

    @GetMapping("/findAutho/{account}")//查询account对应的客户，返回的是客户的公司名
    public List<String> findAutho(@PathVariable("account") String account){
        return dealerService.findAutho(account);
    }

}
