package com.baizhi.bts.service;

import com.baizhi.bts.entity.Guru;

import java.util.List;

public interface GuruService {
    //查所有
    public  List<Guru> queryAll();
    //分页查所有
    public List<Guru> queryPage(Integer page, Integer size);
    //查一个
    public Guru queryOne(String id);
    //添加
    public void insert(Guru guru);
    //修改
    public void update(Guru guru);
    //删除
    public void delete(String[] id);
}
