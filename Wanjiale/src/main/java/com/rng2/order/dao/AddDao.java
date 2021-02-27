package com.rng2.order.dao;

import com.rng2.order.bean.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 12:38
 * Description: No Description
 */
@Mapper
public interface AddDao {
    public Dealer findDealer(int id);
    public List<Warehouse> findHouses();
    public List<Product> findProducts(int houseId);
    public void addOrder(List<Order> orders);
    public void addTotal(TotalOrder totalOrder);
    public List<TotalOrder> findtotal(Select select);
}
