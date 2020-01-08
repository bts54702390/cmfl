<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <script type="text/javascript">
        $(function () {
            $("#UserTable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/user/queryPage",
                    datatype : "json",
                    colNames : [ "id", "手机号", "密码", "盐","状态","头像","姓名","法号","性别","签名","地址","注册时间","最后登录时间"],
                    colModel : [
                        {name: "id",width :350},
                        {name: "phone",editable:true,editrules:{required:true}},
                        {name: "password",editable:true,editrules:{required:true}},
                        {name:"salt",editable:true,editrules:{required:true}},
                        {
                            name:"status",width :100,editrules:{required:true},editable:true,
                            formatter:function (start) {
                                if(start=="1"){
                                    return "正常"
                                }else {
                                    return "冻结"
                                }
                            },edittype:"select",editoptions: {value:"1:正常;2:冻结"}
                        },
                        {
                            name: "photo",editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                            formatter:function (date) {
                                return "<img width='100' height='50' src='"+date+"'></img>"
                            }
                        },
                        {name:"name",editable:true,editrules:{required:true}},
                        {name:"nickName",editable:true,editrules:{required:true}},
                        {
                            name:"sex",width :100,editrules:{required:true},editable:true,
                            formatter:function (start) {
                                if(start=="0"){
                                    return "男"
                                }else {
                                    return "女"
                                }
                            },edittype:"select",editoptions: {value:"0:男;1:女"}
                       },
                        {name:"sign",editable:true,editrules:{required:true}},
                        {name:"location",editable:true,editrules:{required:true}},
                        {name:"rigestDate",editable:true,edittype: "date",editrules:{required:true}},
                        {name:"lastLogin",hidden:true}

                    ],
                    rowNum:3,
                    page:1,
                    rowList : [1, 2, 3, 5,7],
                    pager : '#pager',
                    sortname : 'id',
                    mtype : "post",
                    viewrecords : true,
                    sortorder : "desc",
                    caption : "轮播图",
                    autowidth: true,//只适应宽度
                    multiselect:true,//开启多选框
                    styleUI:"Bootstrap",
                    height:"300px",//宽度设定
                    editurl:"${pageContext.request.contextPath}/user/iud"
                });
            jQuery("#UserTable").jqGrid('navGrid', '#pager',{add:true,edit:true,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                        var id= response.responseJSON.userId;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/updateImg",
                            type: "post",
                            data:{userId:id},
                            fileElementId:"photo",
                            success:function (date) {
                                $("#UserTable").trigger("reloadGrid");//上传完成后刷新表格
                            },
                            dataType:"json"
                        });
                        return postData;
                    }
                },
                {
                    closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var id= response.responseJSON.userId;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/user/updateImg",
                            type: "post",
                            data:{userId:id},
                            fileElementId:"photo",
                            success:function (date) {
                                $("#UserTable").trigger("reloadGrid");//上传完成后刷新表格
                            },
                            dataType:"json"
                        });
                        return postData;
                    }

                });


        })
    </script>
</head>
<body>
<div class="page-header">
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>用户信息</a></li>
</ul>
<div class="panel">
    <table id="UserTable" border="1" style="text-align: center"></table>
    <div id="pager" style="height: 40px"></div>
</div>
</body>