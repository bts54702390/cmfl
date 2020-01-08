package com.baizhi.bts.service;

import com.baizhi.bts.entity.Album;

import java.util.List;

public interface AlbumService {
    //分页查询
    public List<Album> queryPage(Integer page,Integer size);
    //查一个
    public Album qureyOne(String id );
    //添加
    public void inert (Album album);
    //修改
    public void update(Album album);
    //删除
    public void delete(String[] id);
}
