package com.editor.account.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/6
 * Time: 20:15
 * Description: No Description
 */
@Data
public class Client {
    private String code;
    private String name;
    private int quota;
    private int shipAmount;
    private int discountAmount;
    private int pay;
    private String info;
}
