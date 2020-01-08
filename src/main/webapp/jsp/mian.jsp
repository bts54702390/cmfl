<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/jquery.form.js"></script>
    <script src="${pageContext.request.contextPath}/boot/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/echarts/china.js"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="#">欢迎<span class="glyphicon glyphicon-user">：${admin.username}</span></a></li>
            <li><a href="${pageContext.request.contextPath}/admin/outAdmin"><span class="glyphicon glyphicon-log-in"></span> 退出登录</a></li>
        </ul>
    </div>
</nav>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">
            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                               用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse ">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/user.jsp')">用户信息</a></li>
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/userTime.jsp')">用户注册时间</a></li>
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/userLocation.jsp')">用户地址分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                                上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/guru.jsp')">上师信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                               文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/article.jsp')">文章信息</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                               专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/Album.jsp')">专辑信息</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion"
                                   href="#collapsefive">
                                    轮播图管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapsefive" class="panel-collapse collapse ">
                            <div class="panel-body">
                                <ul class="nav">
                                    <li><a href="javascript:$('#bannerimg').load('${pageContext.request.contextPath}/jsp/banner.jsp')">轮播图信息</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-10" id="bannerimg">
            <div class="jumbotron" >
                <div class="container" >
                    <h4>欢迎使用持明法洲后台管理系统</h4>
                </div>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img   src="${pageContext.request.contextPath}/img/ht.jpg" alt="First slide">
                            <div class="carousel-caption">主题 1</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/s.jpg" alt="Second slide">
                            <div class="carousel-caption">主题 2</div>
                        </div>
                        <div class="item">
                            <img src="${pageContext.request.contextPath}/img/xy.jpg" alt="Third slide">
                            <div class="carousel-caption">主题 3</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="panel-footer">
    <h4 style="text-align:center">@百知教育 baizhi@zparkhr.com.cn</h4>
</div>
</body>