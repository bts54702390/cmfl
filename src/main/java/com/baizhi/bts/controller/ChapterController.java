package com.baizhi.bts.controller;

import com.baizhi.bts.dao.ChapterDao;

import com.baizhi.bts.entity.Album;
import com.baizhi.bts.entity.Chapter;
import com.baizhi.bts.service.AlbumService;
import com.baizhi.bts.service.ChapterService;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private AlbumService albumService;
    //分页查询
    @RequestMapping("/qureyPage")
    @ResponseBody
    public Map qureyPage(Integer page, Integer rows,String albumId){
        HashMap hashMap = new HashMap();
        //传递当前页
        hashMap.put("page",page);
        //传递总行数
        Chapter chapter1 = new Chapter();
        chapter1.setAlbumId(albumId);
        int i = chapterDao.selectCount(chapter1);
        hashMap.put("records",i);
        //传递总页数
        Integer tatol=i%rows==0 ? i/rows:i/rows+1;
        hashMap.put("total",tatol);
        //传递查询到的数据
        List<Chapter> chapter = chapterService.qureyPage(page, rows,albumId);
        hashMap.put("rows",chapter);
        return hashMap;
    }
    //添加删除修改
    @RequestMapping("/img")
    @ResponseBody
    public Map img(Chapter chapter,String[] id,String oper,String albumId ){
        HashMap hashMap = new HashMap();
        //添加
        if(oper.equals("add")){
            String ids = UUID.randomUUID().toString().replace("-", "");
            chapter.setId(ids);
            chapter.setAlbumId(albumId);
            hashMap.put("chapterId",ids);
            //修改专辑的集数
            Album album = albumService.qureyOne(albumId);
            album.setId(albumId);
           int count= album.getCount()+1;
            album.setCount(count);
            albumService.update(album);
            //---------
            chapterService.insert(chapter);
        }else if(oper.equals("edit")){
            //修改
            hashMap.put("chapterId",chapter.getId());
            chapterService.update(chapter);
        }else{
            //删除
            chapterService.delet(id);
            //修改集数
            Album album = albumService.qureyOne(albumId);
            album.setId(albumId);
            int count= album.getCount()-1;
            album.setCount(count);
            albumService.update(album);
        }
        return hashMap;
    }
    //修改音频的路径
    @RequestMapping("/updateImg")
    @ResponseBody
    public void updateImg(MultipartFile url,String chaptersd, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/uploda/chapter/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name= new Date().getTime()+""+url.getOriginalFilename();
        //获取协议头
        String http = request.getScheme();
        //获取IP地址
        String  host = InetAddress.getLocalHost().toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名称
        String path = request.getContextPath();
        //拼接网络地址
        String load=http+"://"+host+":"+port+path+"/uploda/chapter/"+name;
        try {
            File file1 = new File(realPath, name);//获取文件对象
            url.transferTo(file1);
            Chapter chapter=new Chapter();
            chapter.setId(chaptersd);
            /*获取文件时长*/
            Encoder encoder = new Encoder();
            MultimediaInfo multimediaInfo = encoder.getInfo(file1);//传入文件对象
            long ls=multimediaInfo.getDuration()/1000;//得到long类型的时长
            chapter.setTime(String.valueOf(ls/60)+":"+String.valueOf(ls%60)); //转换为分钟:秒
            //获取文件大小
            double length = file1.length()/1024/1024;
            System.out.println("文件大小："+length);
            chapter.setSize(length+"MB");
            chapter.setUrl(load);
            chapterService.update(chapter);
        } catch (IOException | EncoderException e) {
            e.printStackTrace();
        }
    }

}
