package com.rng2.order.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/18
 * Time: 15:53
 * Description: No Description
 */
@Data
public class Select {
    private int userid;
    private String orderid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Hongkong")
    private Date timestart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Hongkong")
    private Date timeend;
    private int status;
    private int pricelow;
    private int pricehigh;
    private int other;
}
