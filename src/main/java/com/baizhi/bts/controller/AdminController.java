package com.baizhi.bts.controller;

import com.baizhi.bts.conf.CreateValidateCode;
import com.baizhi.bts.entity.Admin;
import com.baizhi.bts.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    //登录查询
    @RequestMapping("/qureyOne")
    @ResponseBody
    public String qureyOne(String name, String password,HttpServletRequest request,String code){
        HttpSession session = request.getSession(true);
        String  imgcode = session.getAttribute("code").toString();
        String test;
        Admin admin = adminService.queryOne(name);
        if(imgcode.equals(code)){
            if(admin==null){
                test="userno";
            }else if(admin.getPassword().equals(password)){
                session.setAttribute("admin",admin);
                test="ok";
            }else{
                test="passno";
            }
        }else{
            test="imgno";
        }
       // System.out.println(admin);
        return test;
    }
    //获取验证码
    @RequestMapping("/quertCode")
    @ResponseBody
    public String quertCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CreateValidateCode img=new CreateValidateCode();
        //获取验证码
        String code = img.getCode();
        //获取照片
        img.write(response.getOutputStream());
        //将验证码存入session
        HttpSession session = request.getSession(true);
        session.setAttribute("code", code);
        return null;
    }
    //退出登录b
    @RequestMapping("/outAdmin")
    public String outAdmin(HttpSession session){
        session.removeAttribute("admin");
        return "jsp/login";
    }
}
