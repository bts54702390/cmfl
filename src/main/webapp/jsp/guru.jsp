<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <script type="text/javascript">
        $(function () {
            $("#gurutable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/guru/queryPage",
                    datatype : "json",
                    colNames : [ "id", "上师姓名", "头像", "状态","法号"],
                    colModel : [
                        {name: "id"}, {name: "name",editable:true,editrules:{required:true}},
                        {
                            name: "photo",editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                            formatter:function (date) {
                                return "<img width='100' height='50' src='"+date+"'></img>"
                            }
                        },
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
                        {name:"nickName",editable:true,editrules:{required:true}},
                    ],
                    rowNum:3,
                    page:1,
                    rowList : [1, 2, 3, 5,7],
                    pager : '#pager',
                    sortname : 'id',
                    mtype : "post",
                    viewrecords : true,
                    sortorder : "desc",
                    caption : "上师",
                    autowidth: true,//只适应宽度
                    multiselect:true,//开启多选框
                    styleUI:"Bootstrap",
                    height:"300px",//宽度设定
                    editurl:"${pageContext.request.contextPath}/guru/iud"
                });
            jQuery("#gurutable").jqGrid('navGrid', '#pager',{add:true,edit:true,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                        var id= response.responseJSON.guruid;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/guru/updateImg",
                            type: "post",
                            data:{guruId:id},
                            fileElementId:"photo",
                            success:function (date) {
                                $("#gurutable").trigger("reloadGrid");
                            },
                            dataType:"json"
                        });
                        return postData;
                    }
                },
                {
                    closeAfterAdd: true,
                   afterSubmit:function (response,postData) {
                     var id= response.responseJSON.guruid;
                     $.ajaxFileUpload({
                         url: "${pageContext.request.contextPath}/guru/updateImg",
                         type: "post",
                         data:{guruId:id},
                         fileElementId:"photo",
                         success:function (date) {
                             $("#gurutable").trigger("reloadGrid");
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
    <h4>上师管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>上师信息</a></li>
</ul>
<div class="panel">
    <table id="gurutable" border="1" style="text-align: center"></table>
    <div id="pager" style="height: 40px"></div>
</div>
</body>
