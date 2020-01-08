package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.conf.MyLog;
import com.baizhi.bts.dao.GuruDao;
import com.baizhi.bts.entity.Guru;
import com.baizhi.bts.service.GuruService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class GuruServiceImp implements GuruService {
    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Guru> queryPage(Integer page, Integer size) {
        Integer mypage=(page-1)*size;
        Guru guru = new Guru();
        RowBounds rowBounds = new RowBounds(mypage,size);
        List<Guru> gurus = guruDao.selectByRowBounds(guru,rowBounds);
        return gurus;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Guru queryOne(String id) {
        Guru guru = guruDao.selectByPrimaryKey(id);
        return guru;
    }

    @Override
    @MyLog(value = "添加上师")
    public void insert(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    @MyLog(value = "修改上师")
    public void update(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }

    @Override
    @MyLog(value = "删除上师")
    public void delete(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }
}
