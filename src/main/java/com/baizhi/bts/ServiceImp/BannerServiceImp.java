package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.conf.MyLog;
import com.baizhi.bts.dao.BannerDao;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.entity.Pagers;
import com.baizhi.bts.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImp implements BannerService {
   @Autowired
    BannerDao bannerDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> queryAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Banner queryOne(String id) {
        Banner banner = bannerDao.selectByPrimaryKey(id);
        return banner;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> queryPage(Integer page,Integer size) {
        Banner banner1 =new Banner();
        RowBounds rowBounds=new RowBounds(page,size);
        List<Banner> banners = bannerDao.selectByRowBounds(banner1, rowBounds);
        return banners;
    }
    //分页查询
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Pagers querytPages(Integer page, Integer size) {
       Pagers pagers=new Pagers();
        //赋值当前页
       pagers.setPage(page);
       //查询出总行数
        int i = bannerDao.selectCount(new Banner());
        //赋值总行数
        pagers.setRecords(i);
        //计算总页数
        pagers.setTotal(i%size==0 ? i/size : i/size+1 );
        //分页查询初数据
        Banner banner1 =new Banner();
        //运算分页位置
        int mypage=(page-1)*size;
        RowBounds rowBounds=new RowBounds(mypage,size);
        List<Banner> banners = bannerDao.selectByRowBounds(banner1, rowBounds);
        pagers.setRows(banners);
        return pagers;
    }

    @Override
    @MyLog(value = "添加轮播图")
    public void insert(Banner banner) {
        bannerDao.insert(banner);
    }

    @Override
    @MyLog(value = "修改轮播图")
    public void update(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    @MyLog(value = "删除轮播图")
    public void delet(String id) {
        bannerDao.deleteByPrimaryKey(id);
    }
}
