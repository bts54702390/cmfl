package com.baizhi.bts.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.bts.dao.BannerDao;
import com.baizhi.bts.entity.Banner;
import com.baizhi.bts.entity.BannerListener;
import com.baizhi.bts.entity.Pagers;
import com.baizhi.bts.service.BannerService;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    BannerDao bannerDao;
    //查所有
    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Banner> queryAll(){
        List<Banner> banners = bannerService.queryAll();
        return banners;
    }
    //分页查询
    @RequestMapping("/queryPage")
    @ResponseBody
    public Pagers queryPage(Integer page, Integer rows){
        Pagers pagers = bannerService.querytPages(page, rows);
        return pagers;
    }
    //删除添加修改
    @RequestMapping("/imd")
    @ResponseBody
    public Map imd(Banner banner, String oper,String [] id ,HttpServletRequest request) throws UnknownHostException {
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            //添加
            String ids = UUID.randomUUID().toString().replace("-", "");
            banner.setId(ids);
            //获取协议头
            String http = request.getScheme();
            //获取IP地址
            String  host = InetAddress.getLocalHost().toString().split("/")[1];
            //获取端口号
            int port = request.getServerPort();
            //获取项目名称
            String path = request.getContextPath();
            //拼接网络地址
            String load=http+"://"+host+":"+port+path;
            banner.setHref(load);
            hashMap.put("bannerid",ids);
            bannerService.insert(banner);
        }else if(oper.equals("edit")){
            //修改
            String ids = banner.getId();
            hashMap.put("bannerid",ids);
            bannerService.update(banner);
        }else {
            //删除
            //bannerService.delet(banner.getId());
          /*  List<String> strings = Arrays.asList(id);
            for (String sid : strings) {
                Banner one = bannerService.queryOne(sid);
                new File(one.getUrl()).delete();
            }*/
            bannerDao.deleteByIdList(Arrays.asList(id));
        }
        return hashMap;
    }
    //修改图片路径
    @RequestMapping("/updateImg")
    @ResponseBody
    public void updateImg(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/uploda/img/");
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
        String load=http+"://"+host+":"+port+path+"/uploda/img/"+name;
        try {
            url.transferTo(new File(realPath,name));
            Banner banner=new Banner();
            banner.setId(bannerId);
            banner.setUrl(load);
            bannerService.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //导出
   @SneakyThrows
   @RequestMapping("/outpoi")
   @ResponseBody
    public String  outpoi(){
        //指定导出的路径及文件名
       String log;
       try {
           String name="D:\\第三阶段\\后期项目\\day7-poiEasyExcel\\示例\\"+new Date().getTime()+"poi.xls";
           // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
           EasyExcel.write(name,Banner.class).sheet("轮播图").doWrite(bannerService.queryAll());
           log="导出成功";
       }catch (Exception e){
           log="导出失败";
       }
          return log;

    }

    //导入
    @SneakyThrows
    @RequestMapping("/insertImg")
    @ResponseBody
    public String insertImg(MultipartFile imgchange,HttpSession session){
        String insert;
        String realPath = session.getServletContext().getRealPath("/uploda/Excel");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name = new Date().getTime() + "_" + imgchange.getOriginalFilename();
        imgchange.transferTo(new File(realPath,name));
        try {
            File file1 = new File(realPath, name);
            EasyExcel.read(file1,Banner.class,new BannerListener()).sheet().doRead();
            insert="导入成功";
        }catch (Exception e){
            insert="导入失败";
        }
        return  insert;
    }
}
