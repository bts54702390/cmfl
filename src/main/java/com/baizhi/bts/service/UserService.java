package com.baizhi.bts.service;

import com.baizhi.bts.entity.User;
import com.baizhi.bts.entity.UserDot;

import java.util.List;
import java.util.Map;

public interface UserService {
    //创建时间的查询
    public Map queryTime();
    //查询地区分布
    public List<UserDot>queryLocatio(String sex);
}
