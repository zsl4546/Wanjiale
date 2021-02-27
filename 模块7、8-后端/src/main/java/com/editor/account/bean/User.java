package com.editor.account.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 19:37
 * Description: No Description
 */
@Data
public class User {
    private String account;
    private String password;
    private int admin;
    private String status;
    private String name;
    private String mechanism;
    private String sex;
    private String phone;
    private String mail;
    private String charaName;
}
