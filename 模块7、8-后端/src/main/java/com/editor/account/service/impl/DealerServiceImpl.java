package com.editor.account.service.impl;

import com.editor.account.bean.CDRelationship;
import com.editor.account.bean.Client;
import com.editor.account.bean.Dealer;
import com.editor.account.repository.DealerRepository;
import com.editor.account.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/6
 * Time: 16:33
 * Description: No Description
 */
@Service
public class DealerServiceImpl implements DealerService {
    @Autowired
    private DealerRepository dealerRepository;

    @Override
    public Dealer findDealer(Dealer dealer) {
        return dealerRepository.findDealer(dealer);
    }

    @Override
    public List<Client> findAllClient() {
        return dealerRepository.findAllClient();
    }

    @Override
    public List<Client> findClient(String account) {
        return dealerRepository.findClient(account);
    }

    @Override
    public List<Client> findClientChild(String account) {
        return dealerRepository.findClientChild(account);
    }

    @Override
    public void changePass(Dealer dealer) {
        dealerRepository.changePass(dealer);
    }

    @Override
    public List<Dealer> findDealers(Dealer dealer) {
        return dealerRepository.findDealers(dealer);
    }

    @Override
    public void changeStatus(Dealer dealer) {
        dealerRepository.changeStatus(dealer);
    }

    @Override
    public void deleteDealer(String account) {
        dealerRepository.deleteDealer(account);
    }

    @Override
    public Dealer checkDealer(String account) {
        return dealerRepository.checkDealer(account);
    }

    @Override
    public void addDealer(Dealer dealer) {
        dealerRepository.addDealer(dealer);
    }

    @Override
    public void deleteRelationship(String account) {
        dealerRepository.deleteRelationship(account);
    }

    @Override
    public void addRelationship(List<CDRelationship> list) {
        dealerRepository.addRelationship(list);
    }

    @Override
    public List<String> findAutho(String account) {
        return dealerRepository.findAutho(account);
    }
}
