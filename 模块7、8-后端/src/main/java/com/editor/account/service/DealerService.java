package com.editor.account.service;

import com.editor.account.bean.CDRelationship;
import com.editor.account.bean.Client;
import com.editor.account.bean.Dealer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/6
 * Time: 16:33
 * Description: No Description
 */
public interface DealerService {
    public Dealer findDealer(Dealer dealer);
    public List<Client> findAllClient();
    public List<Client> findClient(String account);
    public List<Client> findClientChild(String account);
    public void changePass(Dealer dealer);
    public List<Dealer> findDealers(Dealer dealer);
    public void changeStatus(Dealer dealer);
    public void deleteDealer(String account);
    public Dealer checkDealer(String account);
    public void addDealer(Dealer dealer);
    public void deleteRelationship(String account);
    public void addRelationship(List<CDRelationship> list);
    public List<String> findAutho(String account);
}
