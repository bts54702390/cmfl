package com.baizhi.bts.service;

import com.baizhi.bts.entity.Chapter;

import java.util.List;

public interface ChapterService {
    //分页查询
    public List<Chapter> qureyPage(Integer page,Integer size,String albumId);
    //查一个
    public Chapter queryOne(String id);
    //添加
    public void insert(Chapter chapter);
    //修改
    public void update(Chapter chapter);
    //删除
    public void delet(String[] id);
}
