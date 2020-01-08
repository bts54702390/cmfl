package com.baizhi.bts.service;

import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.entity.Pagers;

import java.util.List;

public interface BannerService {
    //查所有
    public List<Banner> queryAll();
    //查一个
    public Banner queryOne(String id);
    //分页查询
    public List<Banner> queryPage(Integer page,Integer size);
    //添加
    public void insert(Banner banner);
    //修改
    public void update(Banner banner);
    //删除
    public void delet(String id);
    //第二次分页查询
    public Pagers querytPages(Integer page,Integer size);
}
