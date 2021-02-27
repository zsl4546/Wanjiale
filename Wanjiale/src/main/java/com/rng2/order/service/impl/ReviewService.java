package com.rng2.order.service.impl;

import com.rng2.order.bean.Detail;
import com.rng2.order.dao.ReviewDao;
import com.rng2.order.service.Review;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/19
 * Time: 11:09
 * Description: No Description
 */
@Service
public class ReviewService implements Review {
    @Autowired
    private ReviewDao reviewDao;

    @Override
    public void sta(String orderid, Integer status, String content) {
        reviewDao.sta(orderid, status, content);
    }

    @Override
    public List<Detail> detail(String orderid) {
        return reviewDao.detail(orderid);
    }

}
