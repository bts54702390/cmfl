package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.conf.MyLog;
import com.baizhi.bts.dao.AlbumDao;
import com.baizhi.bts.entity.Album;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class AlbumServiceImp implements AlbumService {
    @Autowired
    AlbumDao albumDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> queryPage(Integer page, Integer size) {
        Album album=new Album();
        int mypage=(page-1)*size;
        RowBounds rowBounds=new RowBounds(mypage,size);
        List<Album> albums = albumDao.selectByRowBounds(album, rowBounds);
        return albums;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Album qureyOne(String id) {
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }

    @Override
    @MyLog(value = "添加专辑")
    public void inert(Album album) {
        albumDao.insert(album);
    }

    @Override
    @MyLog(value = "修改专辑")
    public void update(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    @MyLog(value = "删除专辑")
    public void delete(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }
}
