package com.baizhi.bts.controller;

import com.baizhi.bts.dao.GuruDao;
import com.baizhi.bts.entity.Album;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.entity.Guru;
import com.baizhi.bts.service.GuruService;
import org.aspectj.weaver.ast.Var;
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
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    private GuruService guruService;
    @Autowired
    private GuruDao guruDao;
    //分页查所有
    @RequestMapping("/queryPage")
    @ResponseBody
    public Map queryPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        //传递当前页
        hashMap.put("page",page);
        //传递总行数
        int i = guruDao.selectCount(new Guru());
        hashMap.put("records",i);
        //传递总页数
        Integer tatol=i%rows==0 ? i/rows:i/rows+1;
        hashMap.put("total",tatol);
        //传递查询到的数据
        List<Guru> gurus = guruService.queryPage(page, rows);
        hashMap.put("rows",gurus);
        return  hashMap;
    }
    //添加删除修改
    @RequestMapping("/iud")
    @ResponseBody
    public Map iud(Guru guru, String oper, String [] id , HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String ids = UUID.randomUUID().toString().replace("-", "");
            guru.setId(ids);
            hashMap.put("guruid",ids);
            guruService.insert(guru);
        }else if(oper.equals("edit")){
            hashMap.put("guruid",guru.getId());
            guruService.update(guru);
        }else{
            String[] fil=id;
            for (String s : fil) {
                Guru guru1 = guruService.queryOne(s);
                String realPath = session.getServletContext().getRealPath("/uploda/guru/");
                String name =guru1.getPhoto().split("/")[guru1.getPhoto().split("/").length - 1];
                File file = new File(realPath,name);
                file.delete();
            }
            guruService.delete(id);
        }
        return hashMap;
    }
    //修改图片路径
    @RequestMapping("/updateImg")
    @ResponseBody
    public void updateImg(MultipartFile photo,String guruId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/uploda/guru/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name= new Date().getTime()+""+photo.getOriginalFilename();
        //获取协议头
        String http = request.getScheme();
        //获取IP地址
        String  host = InetAddress.getLocalHost().toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名称
        String path = request.getContextPath();
        //拼接网络地址
        String load=http+"://"+host+":"+port+path+"/uploda/guru/"+name;
        try {
            photo.transferTo(new File(realPath,name));
            Guru guru = new Guru();
            guru.setId(guruId);
            guru.setPhoto(load);
            guruService.update(guru);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
