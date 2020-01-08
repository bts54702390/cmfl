package com.baizhi.bts.ServiceImp;

import com.baizhi.bts.conf.MyLog;
import com.baizhi.bts.dao.ChapterDao;
import com.baizhi.bts.entity.Chapter;
import com.baizhi.bts.service.ChapterService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
@Service
@Transactional
public class ChapterServiceImp implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> qureyPage(Integer page, Integer size, String albumId) {
        Integer mypage=(page-1)*size;
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        RowBounds rowBounds = new RowBounds(mypage,size);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, rowBounds);
        return chapters;
    }
    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Chapter queryOne(String id) {
        Chapter chapter = chapterDao.selectByPrimaryKey(id);
        return chapter;
    }

    @Override
    @MyLog(value = "添加专辑目录")
    public void insert(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    @MyLog(value = "修改专辑目录")
    public void update(Chapter chapter) {

        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    @MyLog(value = "删除专辑目录")
    public void delet(String[] id) {
         chapterDao.deleteByIdList(Arrays.asList(id));
    }


}
