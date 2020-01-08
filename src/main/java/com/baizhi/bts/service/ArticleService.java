package com.baizhi.bts.service;

import com.baizhi.bts.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    //分页查询
    public Map queryPage(Integer page, Integer size);
    //查一个
    public Article queryOne(String id);
    //添加
    public void insert(Article article);
    //修改
    public void update(Article article);
    //删除
    public void delete(String[] id);
}
