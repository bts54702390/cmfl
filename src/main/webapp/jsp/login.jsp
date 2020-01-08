<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
          $("#log").click(function () {
              var name= $("#name").val();
              var password=$("#password").val();
              console.log(name);
              console.log(password);
            $.get(
                "${pageContext.request.contextPath}/admin/qureyOne",
                "name="+$("#name").val()+"&password="+$("#password").val()+"&code="+$("#code").val(),
                function (tesm) {
                       if(tesm == "userno"){
                           $("#msg").html("用户不存在！！")
                       }else if(tesm=="imgno"){
                           $("#msg").html("验证码错误！！")
                       }else if(tesm=="passno"){
                           $("#msg").html("密码错误！！")
                       }else{
                           location.href="${pageContext.request.contextPath}/jsp/mian.jsp"
                       }

                },
                "json"
            )
          });
          $("#img").click(function () {
              $("#img").prop("src","${pageContext.request.contextPath}/admin/quertCode?time="+Math.random())
          })
        })
       
    </script>
</head>
<body >


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input id="name" type="text" class="form-control"placeholder="用户名" autocomplete="off" name="name">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input id="code" type="text" class="form-control" placeholder="验证码" autocomplete="off" name="code">
                <img id="img" src="${pageContext.request.contextPath}/admin/quertCode">
            </div>
            <span id="msg" style="color: red"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" >登录</button>
            </div>
            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>

        </div>
        </form>
    </div>
</div>
</body>
</html>
