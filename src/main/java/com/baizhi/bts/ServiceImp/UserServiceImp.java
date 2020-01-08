package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.dao.UserDao;
import com.baizhi.bts.entity.User;
import com.baizhi.bts.entity.UserDot;
import com.baizhi.bts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    UserDao userDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryTime() {
        HashMap hashMap = new HashMap();
        //查询出男客户的创建时间
        ArrayList manlist = new ArrayList();
        manlist.add(userDao.selectTime("0",1));
        manlist.add(userDao.selectTime("0",7));
        manlist.add(userDao.selectTime("0",30));
        manlist.add(userDao.selectTime("0",365));
        ArrayList womanlist = new ArrayList();
        womanlist.add(userDao.selectTime("1",1));
        womanlist.add(userDao.selectTime("1",7));
        womanlist.add(userDao.selectTime("1",30));
        womanlist.add(userDao.selectTime("1",365));
        hashMap.put("man",manlist);
        hashMap.put("woman",womanlist);
        return hashMap;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<UserDot> queryLocatio(String sex) {
        List<UserDot> userDots = userDao.selectLocation(sex);
        return userDots;
    }
}
