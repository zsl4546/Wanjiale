package com.rng2.order.dao;

import com.rng2.order.bean.Detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/19
 * Time: 10:59
 * Description: No Description
 */
@Mapper
public interface ReviewDao {
    public void sta(@Param("orderid")String orderid, @Param("status")Integer status, @Param("content") String content);
    public List<Detail> detail(String orderid);
}
