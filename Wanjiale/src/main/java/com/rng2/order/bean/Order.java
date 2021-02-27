package com.rng2.order.bean;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/17
 * Time: 11:03
 * Description: No Description
 */
@Data
public class Order {
    private int id; //自动生成的订单号，随机串
    private int userid; //经销商id，(session)
    private int productid; //产品id
    private int num; //产品数量
    private String orderid; //订单编码
}
