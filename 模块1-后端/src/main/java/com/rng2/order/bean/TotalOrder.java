package com.rng2.order.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/18
 * Time: 0:42
 * Description: No Description
 */
@Data
public class TotalOrder {
    private String orderid;
    private Date start;
    private Date end;
    private Date ordertime;
    private String info;
    private int shopid;
    private int house;
    private int userid;
    private String shopname;
    private String housename;
    private String name;
    private String account;
    private float totalPrice;
    private int totalNum;
    private float totalVolume;
    private int status;
}
