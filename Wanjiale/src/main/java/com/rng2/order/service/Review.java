package com.rng2.order.service;

import com.rng2.order.bean.Detail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/19
 * Time: 11:08
 * Description: No Description
 */
public interface Review {
    public void sta(String orderid, Integer status, String content);
    public List<Detail> detail(String orderid);
}
