package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.conf.MyLog;
import com.baizhi.bts.dao.ArticleDao;
import com.baizhi.bts.entity.Article;
import com.baizhi.bts.entity.Guru;
import com.baizhi.bts.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map queryPage(Integer page, Integer size) {
        HashMap hashMap = new HashMap();
        //传递当前页
        hashMap.put("page",page);
        //传递总行数
        int i = articleDao.selectCount(new Article());
        hashMap.put("records",i);
        //传递总页数
        Integer tatol=i%size==0 ? i/size:i/size+1;
        hashMap.put("total",tatol);
        Integer mypage=(page-1)*size;
        RowBounds rowBounds = new RowBounds(mypage,size);
        Article article = new Article();
        List<Article> articles = articleDao.selectByRowBounds(article, rowBounds);
        hashMap.put("rows",articles);
        return hashMap;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article queryOne(String id) {
        Article article = articleDao.selectByPrimaryKey(id);
        return article;
    }

    @Override
    @MyLog(value = "添加文章")
    public void insert(Article article) {
        articleDao.insert(article);
    }

    @Override
    @MyLog(value = "修改文章")
    public void update(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    @MyLog(value = "删除文章")
    public void delete(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }
}
