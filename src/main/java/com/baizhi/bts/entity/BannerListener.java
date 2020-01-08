package com.baizhi.bts.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.bts.dao.BannerDao;
import org.springframework.beans.factory.annotation.Autowired;

public class BannerListener extends AnalysisEventListener<Banner> {
   @Autowired
    BannerDao bannerDao;
    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        System.out.println(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
