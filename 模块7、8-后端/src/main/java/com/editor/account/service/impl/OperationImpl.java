package com.editor.account.service.impl;

import com.editor.account.bean.Chara;
import com.editor.account.bean.Mechanism;
import com.editor.account.bean.Page;
import com.editor.account.repository.OperationRepository;
import com.editor.account.service.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 23:43
 * Description: No Description
 */
@Service
public class OperationImpl implements Operation {
    @Autowired
    private OperationRepository operationRepository;

    @Override
    public List<String> findAllCharasName() {
        return operationRepository.findAllCharasName();
    }

    public List<Chara> findCharas(Chara chara){
        return operationRepository.findCharas(chara);
    }

    @Override
    public void changeStatus(Chara chara) {
        operationRepository.changeStatus(chara);
    }

    @Override
    public void deleteChara(Chara chara) {
        operationRepository.deleteChara(chara);
    }

    @Override
    public List<String> findMechanism(Chara chara) {
        return operationRepository.findMechanism(chara);
    }

    @Override
    public List<String> findPage(Chara chara) {
        return operationRepository.findPage(chara);
    }

    @Override
    public List<String> findAllMechanism() {
        return operationRepository.findAllMechanism();
    }

    @Override
    public List<String> findAllPage() {
        return operationRepository.findAllPage();
    }

    @Override
    public void deleteMechanism(String name) {
        operationRepository.deleteMechanism(name);
    }

    @Override
    public void deletePage(String name) {
        operationRepository.deletePage(name);
    }

    @Override
    public void insertMechanism(Mechanism mechanism) {
        operationRepository.insertMechanism(mechanism);
    }

    @Override
    public void insertPage(Page page) {
        operationRepository.insertPage(page);
    }

    @Override
    public void addChara(Chara chara) {
        operationRepository.addChara(chara);
    }
}
