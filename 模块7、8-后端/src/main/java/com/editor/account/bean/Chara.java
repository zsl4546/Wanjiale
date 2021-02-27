package com.editor.account.bean;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 23:37
 * Description: No Description
 */
@Data
public class Chara {
    private String name;
    private String status; //1代表启用，2代表禁用
    private String description;
}
