package com.rng2.order.bean;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/19
 * Time: 18:02
 * Description: No Description
 */
@Data
public class Detail {
    private String orderid;
    private Integer num;
    private int id;
    private int houseid;
    private String code;
    private String model;
    private String category;
    private int quantity;
    private float invoicing;
    private float amount;
    private float volume;
}
