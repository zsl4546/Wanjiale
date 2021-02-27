package com.rng2.order.controller;

import com.rng2.order.bean.Detail;
import com.rng2.order.service.impl.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/19
 * Time: 11:11
 * Description: No Description
 */
@RestController
@RequestMapping("/review")
public class Review {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/first/{orderid}/{status}/{content}")
    public void sta(@PathVariable("orderid") String orderid, @PathVariable("status") Integer status, @PathVariable("content") String content){
        System.out.println(orderid + "===" + status + "===" + content);
        reviewService.sta(orderid, status, content);
    }

    @GetMapping("/second/{orderid}/{status}")
    public void requestRe(@PathVariable("orderid") String orderid, @PathVariable("status") Integer status){
        reviewService.sta(orderid, status, "");
    }

    @GetMapping("/detail/{orderid}")
    public List<Detail> detail(@PathVariable("orderid") String orderid){
        return reviewService.detail(orderid);
    }
}
