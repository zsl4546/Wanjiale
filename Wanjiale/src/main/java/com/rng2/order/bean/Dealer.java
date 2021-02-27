package com.rng2.order.bean;

import lombok.Data;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 12:44
 * Description: No Description
 */
@Data
public class Dealer {
    private int id;
    private String name;
    private String code;
    private String reg;
    private List<Shop> shops;
}
