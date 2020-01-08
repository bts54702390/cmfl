package com.baizhi.bts.controller;

import com.baizhi.bts.conf.MyNote;
import com.baizhi.bts.dao.*;
import com.baizhi.bts.entity.*;
import com.baizhi.bts.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/userstage")
public class UserStageController {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    CourseDao courseDao;
    @Autowired
    CounterDao counterDao;
    @Autowired
    GuruService guruService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    //登录接口
    @RequestMapping("/longinuser")
    public Map longinuser(String phone,String password){
        HashMap hashMap = new HashMap();
        User user1 = new User();
        user1.setPhone(phone);
        User user = userDao.selectOne(user1);
        if(user!=null){
            if(user.getPassword().equals(password)){
                hashMap.put("status","200");
                hashMap.put("user",user);
            }else {
                hashMap.put("status","-200");
                hashMap.put("message","密码错误！！");
            }
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","用户不存在！！");
        }
        return hashMap;
    }
    //2.0发送验证码
    @RequestMapping("/outPhone")
    public Map outPhone(String phone){
        HashMap hashMap = new HashMap();
        try {
            String code = UUID.randomUUID().toString().replace("-", "").substring(0,4);
            MyNote.Note(phone,code);
            stringRedisTemplate.opsForValue().set("phone",phone);
            stringRedisTemplate.opsForValue().set("code",code);
            stringRedisTemplate.expire("phone",3, TimeUnit.MINUTES);
            stringRedisTemplate.expire("code",3, TimeUnit.MINUTES);
            hashMap.put("status","200");
            hashMap.put("message","验证码发送成功,有效时间3分钟！！");
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","验证码发送失败！！");
        }
        return  hashMap;
    }
    //3.0注册接口
    @RequestMapping("/code")
    public Map code(String code){
        HashMap hashMap = new HashMap();
        String codes = stringRedisTemplate.opsForValue().get("code");
        if(codes.equals(code)){
            hashMap.put("status","200");
            hashMap.put("message","验证码验证成功，请继续完成注册！！");
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","验证码验证失败，请重新注册！！");
        }
        return  hashMap;
    }
    //4.0补充个人信息接口
    @PostMapping(value = "/insertOther")
    public Map insertOther(@RequestBody User user){
        HashMap hashMap = new HashMap();
        String phone = stringRedisTemplate.opsForValue().get("phone");
        try {
            String id = UUID.randomUUID().toString().replace("-", "");
            user.setId(id);
            user.setPhone(phone);
            userDao.insert(user);
            user.setRigestDate(new Date());
            hashMap.put("status","200");
            hashMap.put("user",user);
        }catch (Exception e){
            hashMap.put("status","-200");
            hashMap.put("message","注册失败，请重新注册！！");
        }
        return  hashMap;
    }
    //5.0一级页面展示接口
    @RequestMapping("/queryOnePage")
    public Map queryOnePage(String uid,String type,String subType){
        HashMap hashMap = new HashMap();
        if(type.equals("all")){
            List<Banner> banners = bannerService.queryAll();
            List<Album> albums = albumDao.selectAll();
            List<Article> articles = articleDao.selectAll();
            hashMap.put("status","200");
            hashMap.put("banners",banners);
            hashMap.put("albums",albums);
            hashMap.put("articles",articles);
        }else if(type.equals("wen")){
            List<Album> albums = albumDao.selectAll();
            hashMap.put("status","200");
            hashMap.put("albums",albums);
        }else {
            List<Article> articles = articleDao.selectAll();
            hashMap.put("status","200");
            hashMap.put("articles",articles);
        }
        return  hashMap;
    }
    //6.0文章详情接口
    @RequestMapping("/queryArticle")
    public Map queryArticle(String uid,String id){
        HashMap hashMap = new HashMap();
        Article article = articleService.queryOne(id);
        hashMap.put("status","200");
        hashMap.put("article",article);
        return  hashMap;
    }
    //7.0专辑详情接口
    @RequestMapping("/queryAblum")
    public Map queryAblum(String uid,String id){
        HashMap hashMap = new HashMap();
        Album album = albumService.qureyOne(id);
        hashMap.put("status","200");
        hashMap.put("album",album);
        return  hashMap;
    }
    //8.0展示功课
    @RequestMapping("/queryCourse")
    public Map queryCourse(String uid){
        HashMap hashMap = new HashMap();
        Course course = new Course();
        course.setUserId(uid);
        Course course1 = courseDao.selectOne(course);
        hashMap.put("status","200");
        hashMap.put("course",course1);
        return  hashMap;
    }
    //9.0 添加功课
    @RequestMapping("/insertCourse")
    public Map insertCourse(String uid,String title){
        HashMap hashMap = new HashMap();
        Course course = new Course();
        String id = UUID.randomUUID().toString().replace("-", "");
        Date date = new Date();
        course.setId(id);
        course.setTitle(title);
        course.setUserId(uid);
        course.setCreateDate(date);
        courseDao.insert(course);
        hashMap.put("course",course);
        hashMap.put("status","200");
        return  hashMap;
    }
    //10.0 删除功课
    @RequestMapping("/deleteCourse")
    public Map deleteCourse(String uid,String id){
        HashMap hashMap = new HashMap();
        Course course = courseDao.selectByPrimaryKey(id);
        hashMap.put("course",course);
        hashMap.put("status","200");
        courseDao.deleteByPrimaryKey(id);
        return  hashMap;
    }
    //11.0展示计数器
    public Map queryCounter(String uid,String id){
        HashMap hashMap = new HashMap();
        Counter counter = counterDao.selectByPrimaryKey(id);
        hashMap.put("counter",counter);
        hashMap.put("status","200");
        return  hashMap;
    }
    //12.0添加计数器
    @RequestMapping("/insertCounter")
    public Map insertCounter(String uid,String title){
        HashMap hashMap = new HashMap();
        Counter counter = new Counter();
        String id = UUID.randomUUID().toString().replace("-", "");
        counter.setId(id);
        counter.setCount(1);
        counter.setTitle(title);
        counter.setCreateDate(new Date());
        counter.setUserId(uid);
        counterDao.insert(counter);
        hashMap.put("counter",counter);
        hashMap.put("status","200");
        return  hashMap;
    }
    //13.0 删除计数器
    @RequestMapping("/deleteCounter")
    public Map deleteCounter(String uid,String id){
        HashMap hashMap = new HashMap();
        Counter counter = counterDao.selectByPrimaryKey(id);
        hashMap.put("counter",counter);
        hashMap.put("status","200");
        counterDao.deleteByPrimaryKey(id);
        return  hashMap;
    }
   //14.0 表更计数器
    @RequestMapping("/updateCounter")
    public Map updateCounter(String uid,String id,Integer count){
        HashMap hashMap = new HashMap();
        Counter counter = counterDao.selectByPrimaryKey(id);
        counter.setId(id);
        counter.setUserId(uid);
        counter.setCount(count);
        counterDao.updateByPrimaryKeySelective(counter);
        hashMap.put("counter",counter);
        hashMap.put("status","200");
        return  hashMap;
    }
    //15.0修改个人信息
    @RequestMapping("/updateUser")
    public Map updateUser(User user){
        HashMap hashMap = new HashMap();
        userDao.updateByPrimaryKeySelective(user);
        hashMap.put("user",user);
        hashMap.put("status","200");
        return  hashMap;
    }
    //16.0金刚道友
    @RequestMapping("/queryOneUser")
    public Map queryOneUser(String uid){
        HashMap hashMap = new HashMap();
        User user = userDao.selectByPrimaryKey(uid);
        hashMap.put("user",user);
        return  hashMap;
    }
    //17.0展示上师列表
    @RequestMapping("/queryGuru")
    public Map queryGuru(String uid){
        HashMap hashMap = new HashMap();
        List<Guru> gurus = guruService.queryAll();
        hashMap.put("status","200");
        hashMap.put("message","以下为所有上师");
        hashMap.put("gurus",gurus);

        return  hashMap;
    }
    // 18.0添加关注上师

}
