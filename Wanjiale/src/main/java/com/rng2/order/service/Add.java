package com.rng2.order.service;

import com.rng2.order.bean.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 14:00
 * Description: No Description
 */
public interface Add {
    public Dealer findDealer(int id);
    public List<Warehouse> findHouses();
    public List<Product> findProducts(int houseId);
    public void addOrder(List<Order> orders);
    public void addTotal(TotalOrder totalOrder);
    public List<TotalOrder> findtotal(Select select);
}
