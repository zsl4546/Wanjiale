package com.editor.account.repository;

import com.editor.account.bean.Chara;
import com.editor.account.bean.Mechanism;
import com.editor.account.bean.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/1/3
 * Time: 23:37
 * Description: No Description
 */
@Mapper
public interface OperationRepository {
    public List<String> findAllCharasName();
    public List<Chara> findCharas(Chara chara);
    public void changeStatus(Chara chara);
    public void deleteChara(Chara chara);
    public List<String> findMechanism(Chara chara);
    public List<String> findPage(Chara chara);
    public List<String> findAllMechanism();
    public List<String> findAllPage();
    public void deleteMechanism(String name);
    public void deletePage(String name);
    public void insertMechanism(Mechanism mechanism);
    public void insertPage(Page page);
    public void addChara(Chara chara);
}
