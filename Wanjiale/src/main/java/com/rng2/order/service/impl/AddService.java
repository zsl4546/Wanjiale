package com.rng2.order.service.impl;

import com.rng2.order.bean.*;
import com.rng2.order.dao.AddDao;
import com.rng2.order.service.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2020/12/16
 * Time: 14:00
 * Description: No Description
 */
@Service
public class AddService implements Add {
    @Autowired
    private AddDao addDao;

    @Override
    public Dealer findDealer(int id) {
        return addDao.findDealer(id);
    }

    @Override
    public List<Warehouse> findHouses() {
        return addDao.findHouses();
    }

    @Override
    public List<Product> findProducts(int houseId) {
        return addDao.findProducts(houseId);
    }

    @Override
    public void addOrder(List<Order> orders) {
        addDao.addOrder(orders);
    }

    @Override
    public void addTotal(TotalOrder totalOrder) {
        addDao.addTotal(totalOrder);
    }

    @Override
    public List<TotalOrder> findtotal(Select select) {
        return addDao.findtotal(select);
    }

}
