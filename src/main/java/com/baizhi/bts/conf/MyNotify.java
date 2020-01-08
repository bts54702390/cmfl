package com.baizhi.bts.conf;

import com.baizhi.bts.dao.CmflLogDao;
import com.baizhi.bts.entity.Admin;
import com.baizhi.bts.entity.Cmfllog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

@Aspect//标记为通知类
@Component//交给工厂管理
@Slf4j
public class MyNotify {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    CmflLogDao cmflLogDao;

    @Around(value = "myPointcut()")
    public Object myAround(ProceedingJoinPoint pro) throws Throwable {
        //先获取用户
        Admin admin = (Admin)request.getSession(true).getAttribute("admin");
        //获取用户名
        String username = admin.getUsername();
        Object object=null;
        //操作时间
        Date date = new Date();
        //状态
        String  tolg=null;
        //获取方法对象
        MethodSignature signature = (MethodSignature)pro.getSignature();
        Method method = signature.getMethod();
        //获取所注解的对象,以及注解中包含的属性值
        MyLog myLog = method.getAnnotation(MyLog.class);
        String value = myLog.value();
        try {
            object = pro.proceed();
            tolg="执行成功";
            return object;
        }catch (Throwable throwable){
            tolg="执行失败";
            throw throwable;
        }finally {
            Cmfllog cmfllog = new Cmfllog();
            String id = UUID.randomUUID().toString().replace("-", "");
            cmfllog.setId(id);
            cmfllog.setName(username);
            cmfllog.setDate(date);
            cmfllog.setMethod(value);
            cmfllog.setTolg(tolg);
            cmflLogDao.insert(cmfllog);
            log.info("管理员：{},在：{},执行了{}，{}",username,date,value);
        }
    }
    @Pointcut(value = "@annotation(com.baizhi.bts.conf.MyLog)")
    public void myPointcut(){

    }
}
