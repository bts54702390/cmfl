package com.baizhi.bts.controller;

import com.baizhi.bts.dao.GuruDao;
import com.baizhi.bts.entity.Article;
import com.baizhi.bts.entity.Guru;
import com.baizhi.bts.service.ArticleService;
import com.baizhi.bts.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.fileupload.FileItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private GuruService guruService;
    @Autowired
    private GuruDao guruDao;
    //分页查询
    @RequestMapping("/queryPage")
    @ResponseBody
    public Map queryPage(Integer page, Integer rows){
        Map map = articleService.queryPage(page, rows);
        return map;
    }
    //查询所有的上师
    @RequestMapping("/queryGurn")
    @ResponseBody
    public List<Guru>queryGurn(){
        List<Guru> gurus = guruService.queryAll();
        return  gurus;
    }
    //添加
    @RequestMapping("/insertArticle")
    @ResponseBody
    public void insertArticle(Article article,MultipartFile imgchange,HttpSession session, HttpServletRequest request) throws IOException {
        String realPath = session.getServletContext().getRealPath("/uploda/article/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        System.out.println(imgchange+"*****");
        System.out.println(article+"开始*》》》》");
        String name= new Date().getTime()+"_"+imgchange.getOriginalFilename();
        //获取协议头
        String http = request.getScheme();
        //获取IP地址
        String  host = InetAddress.getLocalHost().toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名称
        String path = request.getContextPath();
        //拼接网络地址
        String load=http+"://"+host+":"+port+path+"/uploda/article/"+name;
        imgchange.transferTo(new File(realPath,name));
       if(article.getId()==null || "".equals(article.getId())){
           System.out.println(article.getId());
           String newid = UUID.randomUUID().toString().replace("-", "");
           article.setId(newid);
           article.setImg(load);
           articleService.insert(article);
       }else {
           System.out.println(article.getId());
           article.setImg(load);
           System.out.println(article+"修改》》》》");
           articleService.update(article);
       }


    }
    //修改
    //删除
    @RequestMapping("/detet")
    @ResponseBody
    public void detet(String [] id , HttpServletRequest request, HttpSession session){
        String[] fil=id;
        //删除对应的图片
        for (String s : fil) {
            Article article = articleService.queryOne(s);
            String realPath = session.getServletContext().getRealPath("/uploda/article/");
            String name =article.getImg().split("/")[article.getImg().split("/").length - 1];
            File file = new File(realPath,name);
            file.delete();
        }
        articleService.delete(id);
    }

}
