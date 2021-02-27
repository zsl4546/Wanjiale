package com.editor.account.controller;

import com.editor.account.bean.Chara;
import com.editor.account.bean.Mechanism;
import com.editor.account.bean.Page;
import com.editor.account.service.impl.OperationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 23:45
 * Description: No Description
 */
@RestController
@RequestMapping("operation")
public class OperationController {
    @Autowired
    private OperationImpl operation;

    @GetMapping("/findAllCharaName") //查询所有得到角色的名字
    public List<String> findAllCharaName(){
        return operation.findAllCharasName();
    }

    @GetMapping("/findCharas") // 实现查询功能，chara里面有name就筛选name，有status就筛选status
    public List<Chara> findCharas(Chara chara){
        return operation.findCharas(chara);
    }

    @GetMapping("/changeStatus") // 更改角色状态
    public void changeStatus(Chara chara){
        operation.changeStatus(chara);
    }

    @PostMapping("/manyStatus") // 批量更改角色状态
    public void manyStatus(@RequestBody List<Chara> charaList){
        for (Chara chara: charaList){
            operation.changeStatus(chara);
        }
    }

    @PostMapping("/manyDelete") //批量删除角色状态
    public void manyDelete(@RequestBody List<Chara> charaList){
        for (Chara chara:charaList){
            operation.deleteChara(chara);
        }
    }

    @GetMapping("/deleteChara") //删除单个角色
    public void deleteChara(Chara chara){
        operation.deleteChara(chara);
    }

    @GetMapping("/findMechanism") //查询机构，通过chara的名字，也就是查询chara对应的机构
    public List<String> findMechanism(Chara chara){
        return operation.findMechanism(chara);
    }

    @GetMapping("/findPage") //查询页面权限，原理同上
    public List<String> findPage(Chara chara){
        return operation.findPage(chara);
    }

    @GetMapping("/findAllMechanism") //查询所有的机构，用于实现选择框
    public List<String> findAllMechanism(){
        return operation.findAllMechanism();
    }

    @GetMapping("/findAllPage") //查询所有的页面，用于实现选择框
    public List<String> findAllPage(){
        return operation.findAllPage();
    }

    @PostMapping("/insertMechanism/{name}") //给角色分配机构权限
    public void insertMechanism(@RequestBody List<String> mechanismList, @PathVariable("name") String name){
        Chara chara = new Chara();
        chara.setName(name);
        operation.deleteMechanism(name);
//        List<String> allMechanism = operation.findAllMechanism();
//        List<String> oldMechanism = operation.findMechanism(chara);

        Mechanism mechanism = new Mechanism();
        mechanism.setCharaName(name);
        for (String s:mechanismList){
//            if (!oldMechanism.contains(s) && allMechanism.contains(s)){
                mechanism.setMechanism(s);
                operation.insertMechanism(mechanism);
//            }
        }
    }

    @PostMapping("/insertPage/{name}") //给角色分配页面权限
    public void insertPage(@RequestBody List<String> pageList, @PathVariable("name") String name){
        Chara chara = new Chara();
        chara.setName(name);
        List<String> allMechanism = operation.findAllPage();
        List<String> oldMechanism = operation.findPage(chara);

        Page page = new Page();
        page.setCharaName(name);
        for (String s:pageList){
            if (!oldMechanism.contains(s) && allMechanism.contains(s)){
                page.setPage(s);
                operation.insertPage(page);
            }
        }
    }

    @PostMapping("/add") //添加角色
    public void addChara(Chara chara){
        operation.addChara(chara);
    }
}
