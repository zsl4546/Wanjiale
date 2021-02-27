package com.rng2.order.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 17:09
 * Description: No Description
 */
@Data
public class Product {
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
