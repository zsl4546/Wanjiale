package com.rng2.order.controller;

import com.rng2.order.bean.*;
import com.rng2.order.service.impl.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 12:37
 * Description: No Description
 */
@RestController
@RequestMapping("/add")
public class Add {
    @Autowired
    private AddService addService;

    @RequestMapping("find/{id}")
    public Dealer findDealer(@PathVariable("id") int id){
        return addService.findDealer(id);
    }

    @RequestMapping("/houses")
    public List<Warehouse> findHouses(){
        return addService.findHouses();
    }

    @RequestMapping("/products")
    public List<Product> findProducts(int houseId){
        return addService.findProducts(houseId);
    }

    @PostMapping("/submit")
    public void addOrder(@RequestBody List<Order> orderList){
        List<Order> newList = new ArrayList<>();
        for (Order order:orderList){
            if (order.getNum() != 0){
                newList.add(order);
            }
        }
        addService.addOrder(newList);
    }

    @PostMapping("/totalorder")
    public void addTotal(@RequestBody TotalOrder totalOrder){
        totalOrder.setOrdertime(new Date());
        System.out.println(totalOrder);
        addService.addTotal(totalOrder);
    }

    @GetMapping("/findtotal")
    public List<TotalOrder> findtotal(Select select){
        System.out.println(select);
        return addService.findtotal(select);
    }

}
