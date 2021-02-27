package com.editor.account.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/6
 * Time: 16:19
 * Description: No Description
 */
@Data
public class Dealer {
    private String account;
    private String password;
    private String name;
    private String mail;
    private String phone;
    private String status;
    private String fatherAccount;
    private String type;
}
