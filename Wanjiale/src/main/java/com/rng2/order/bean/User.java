package com.rng2.order.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/15
 * Time: 21:19
 * Description: No Description
 */
@Data
public class User {
    private int id;
    private String account;
    private String password;
    private int tag;
    private int code;
}
