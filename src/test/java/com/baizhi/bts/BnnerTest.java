package com.baizhi.bts;

import com.baizhi.bts.dao.BannerDao;
import com.baizhi.bts.entity.Article;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.entity.Chapter;
import com.baizhi.bts.entity.Pagers;
import com.baizhi.bts.service.ArticleService;
import com.baizhi.bts.service.BannerService;
import com.baizhi.bts.service.ChapterService;
import com.sun.deploy.panel.ITreeNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(classes =cmflApp.class )
@RunWith(value = SpringRunner.class)
public class BnnerTest {
    @Autowired
    BannerDao bannerDao;
    @Autowired
    BannerService bannerService;
    @Autowired
    ChapterService chapterService;
    @Autowired
    ArticleService articleService;
    @Test
    public void query(){
       /* Banner banner=new Banner("1","西游","localhost:8989/cmfl/img/bannerimg/ht.jpg",null,new Date(),"历史","1");
        bannerDao.insert(banner);*/
        Banner banner=new Banner("1","西游",null,null,null,"史书","1");
        bannerDao.updateByPrimaryKeySelective(banner);
    }
    @Test
    public void testservce(){
        //查询
       /* List<Banner> banners = bannerService.queryPage(0, 1);
        for (Banner banner : banners) {
            System.out.println(banner);
        }*/
        Pagers pagers = bannerService.querytPages(2, 2);
        List<Banner> rows = pagers.getRows();
        for (Banner row : rows) {
            System.out.println(row);
        }
    }
    @Test
    public  void qureycha(){
        List<Chapter> chapters = chapterService.qureyPage(1, 2, "0872428f42fb458d95008fa88b9bd2c8");
        for (Chapter chapter : chapters) {
            System.out.println(chapter);
        }
    }
    @Test
    public void tesymap(){
        Article article = articleService.queryOne("1");
        System.out.println(article);
    }
}
