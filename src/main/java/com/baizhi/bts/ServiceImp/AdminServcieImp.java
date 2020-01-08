package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.dao.AdminDao;
import com.baizhi.bts.entity.Admin;
import com.baizhi.bts.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServcieImp implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Admin queryOne(String name) {
        Admin admin= new Admin();
        admin.setUsername(name);
        Admin one = adminDao.selectOne(admin);
        return one;
    }
}
