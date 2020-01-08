package com.baizhi.bts;

import com.baizhi.bts.conf.MyNote;
import com.baizhi.bts.dao.AdminDao;
import com.baizhi.bts.dao.UserDao;
import com.baizhi.bts.entity.Admin;
import com.baizhi.bts.entity.User;
import com.baizhi.bts.entity.UserDot;
import com.baizhi.bts.service.AdminService;
import com.baizhi.bts.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = cmflApp.class)
public class cmflTest {
    @Autowired
    AdminDao adminDao;
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void testquery(){
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }

    }
    @Test
    public void testservcei(){
        Admin admin = adminService.queryOne("11");
        System.out.println(admin);
    }
    @Test
    public void testuser(){
        Map map = userService.queryTime();
        System.out.println(map);
    }
    @Test
    public void testlocation(){
        List<UserDot> userDots = userService.queryLocatio("1");
        for (UserDot userDot : userDots) {
            System.out.println(userDot);
        }
    }
    @Test
    public void testnote(){
      MyNote.Note("13061097862","5211314");

    }
    @Test
    public void testredis(){
        String phone="13061097862";
        String code="12354";
        HashOperations<String, String , String > hash = stringRedisTemplate.opsForHash();
        HashMap<String, String> map = new HashMap<>();
        map.put(phone,code);
        hash.putAll("phone",map);

    }
}
