package com.baizhi.bts.controller;

import com.baizhi.bts.dao.AlbumDao;
import com.baizhi.bts.entity.Album;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.service.AlbumService;
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
@RequestMapping("/album")
public class AlbumCotroller {
    @Autowired
    private AlbumService  albumService;
    @Autowired
    private AlbumDao albumDao;
    //分页查询
    @RequestMapping("/queryPage")
    @ResponseBody
    public Map queryPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        //传递当前页
        hashMap.put("page",page);
        //传递总行数
        int i = albumDao.selectCount(new Album());
        hashMap.put("records",i);
        //传递总页数
        Integer tatol=i%rows==0 ? i/rows:i/rows+1;
        hashMap.put("total",tatol);
        //传递查询到的数据
        List<Album> albums = albumService.queryPage(page, rows);
        hashMap.put("rows",albums);
        return hashMap;
    }
    //添加修改删除
    @RequestMapping("/iud")
    @ResponseBody
    public Map iud(Album album, String oper, String [] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            //添加
            String ids = UUID.randomUUID().toString().replace("-", "");
            album.setId(ids);
            album.setCount(0);
            hashMap.put("albumid",ids);
            albumService.inert(album);
        }else if(oper.equals("edit")){
            //修改
            String id1 = album.getId();
            hashMap.put("albumid",id1);
            albumService.update(album);
        }else {
           albumDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }
    //图片及音频路径修改
    @RequestMapping("/updateImg")
    @ResponseBody
    public void updateImg(MultipartFile status, String albumId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/uploda/album/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name= new Date().getTime()+""+status.getOriginalFilename();
        //获取协议头
        String http = request.getScheme();
        //获取IP地址
        String  host = InetAddress.getLocalHost().toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名称
        String path = request.getContextPath();
        //拼接网络地址
        String load=http+"://"+host+":"+port+path+"/uploda/album/"+name;
        try {
            status.transferTo(new File(realPath,name));
            Album album=new Album();
            album.setId(albumId);
            album.setStatus(load);
            albumService.update(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
