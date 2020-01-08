package com.baizhi.bts.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.bts.dao.UserDao;
import com.baizhi.bts.entity.Album;
import com.baizhi.bts.entity.Article;
import com.baizhi.bts.entity.User;
import com.baizhi.bts.entity.UserDot;
import com.baizhi.bts.service.UserService;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
   private  UserService userService;
    @Autowired
    private UserDao userDao;
    //分页查询
    @RequestMapping("/queryPage")
    @ResponseBody
    public Map queryPage(Integer page, Integer rows){
        HashMap hashMap = new HashMap();
        //传递当前页
        hashMap.put("page",page);
        //传递总行数
        int i = userDao.selectCount(new User());
        hashMap.put("records",i);
        //传递总页数
        Integer tatol=i%rows==0 ? i/rows:i/rows+1;
        hashMap.put("total",tatol);
        Integer mypage=(page-1)*rows;
        RowBounds rowBounds = new RowBounds(mypage,rows);
        List<User> users = userDao.selectByRowBounds(new User(), rowBounds);
        hashMap.put("rows",users);
        return  hashMap;
    }
    //创建时间的柱状图
    @RequestMapping("/queryTime")
    @ResponseBody
    public Map queryTime(){
        Map map = userService.queryTime();
        return map;
    }
    //查询用户的地域分布
    @RequestMapping("/queryLOcation")
    @ResponseBody
    public Map queryLOcation(){
      /*
       总的map作为返回的json对象
      */
        HashMap hashMap = new HashMap();
        //带一个集合是为了存储男用户的地域分布
        ArrayList<Map> map1 = new ArrayList<>();
        //带二个集合是为了存储女用户的地域分布
        ArrayList<Map> map2 = new ArrayList<>();

        //先查询出男用户的地域分布
        List<UserDot> userDots = userService.queryLocatio("0");
        for (UserDot userDot : userDots) {
            HashMap man = new HashMap();
            man.put("name",userDot.getName());
            man.put("value",userDot.getValue());
            map1.add(man);
        }
        hashMap.put("man",map1);
        //在查询出女用户的地域分布
        List<UserDot> woman = userService.queryLocatio("1");
        for (UserDot userDot : woman) {
            HashMap womap = new HashMap();
            womap.put("name",userDot.getName());
            womap.put("value",userDot.getValue());
            map2.add(womap);
        }
        hashMap.put("womap",map2);
        return hashMap;
    }
    //添加修改删除
    @RequestMapping("/iud")
    @ResponseBody
    public Map iud(String oper, User user, String[] id, HttpServletRequest request, HttpSession session){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String ids = UUID.randomUUID().toString().replace("-", "");
            user.setId(ids);
            hashMap.put("userId",ids);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3696a5ce7c914d9aaa61fef90e4090d7");
            Map map = queryTime();
            String ss = JSONUtils.toJSONString(map);
            System.out.println("ss = " + ss);
            goEasy.publish("cmfz", ss);
            userDao.insert(user);

        }else if(oper.equals("edit")){
            hashMap.put("userId",user.getId());
            userDao.updateByPrimaryKeySelective(user);
        }else{
            String[] fil=id;
            //删除对应的图片
            for (String s : fil) {
                User one = userDao.selectByPrimaryKey(s);
                String realPath = session.getServletContext().getRealPath("/uploda/user/");
                String name =one.getPhoto().split("/")[one.getPhoto().split("/").length - 1];
                File file = new File(realPath,name);
                file.delete();
            }
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-3696a5ce7c914d9aaa61fef90e4090d7");
            Map map = queryTime();
            String ss = JSONUtils.toJSONString(map);
            System.out.println("ss = " + ss);
            goEasy.publish("cmfz", ss);
            userDao.deleteByIdList(Arrays.asList(id));
        }
        return  hashMap;
    }
    //修改图片路径
    //图片及音频路径修改
    @RequestMapping("/updateImg")
    @ResponseBody
    public void updateImg(MultipartFile photo, String userId, HttpSession session, HttpServletRequest request) throws UnknownHostException {
        String realPath = session.getServletContext().getRealPath("/uploda/user/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name= new Date().getTime()+"_"+photo.getOriginalFilename();
        //获取协议头
        String http = request.getScheme();
        //获取IP地址
        String  host = InetAddress.getLocalHost().toString().split("/")[1];
        //获取端口号
        int port = request.getServerPort();
        //获取项目名称
        String path = request.getContextPath();
        //拼接网络地址
        String load=http+"://"+host+":"+port+path+"/uploda/user/"+name;
        try {
            photo.transferTo(new File(realPath,name));
            User user = new User();
            user.setId(userId);
           user.setPhoto(load);
            userDao.updateByPrimaryKeySelective(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
