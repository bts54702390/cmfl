<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <script type="text/javascript">
        $(function () {
            $("#bannertable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/banner/queryPage",
                    datatype : "json",
                    colNames : [ "id", "标题", "图片展示", "超链接","日期","描述","状态"],
                    //editrules:{required:true}设定该选框必填；edittype:'file'，设定以文件类型传送，适用于文件上传；
                    //editoptions:{enctype:"multipart/form-data"}上传一二进制方式；editoptions: {value:"1:展示;2:冻结"}设定展示的值
                    colModel : [
                        {name: "id",width :350}, {name: "title",width :85,editable:true,editrules:{required:true}},
                        {
                            name: "url",width :100,editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                            formatter:function (date) {
                                return "<img width='100' height='50' src='"+date+"'></img>"
                            }
                        },
                        {name:"href",width :100}, {name:"createDate",width :120,editable:true,edittype: "date",editrules:{required:true}},
                        {name:"descvs",width :100,editable:true,editrules:{required:true}},
                        {
                            name:"status",width :100,editrules:{required:true},editable:true,
                            formatter:function (start) {
                                if(start=="1"){
                                    return "正常"
                                }else {
                                    return "冻结"
                                }
                            },edittype:"select",editoptions: {value:"1:正常;2:冻结"}
                        }
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
                    editurl:"${pageContext.request.contextPath}/banner/imd"
                });
            jQuery("#bannertable").jqGrid('navGrid', '#pager',{add:true,edit:true,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                        var id= response.responseJSON.bannerid;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/banner/updateImg",
                            type: "post",
                            data:{bannerId:id},
                            fileElementId:"url",
                            success:function (date) {
                                $("#bannertable").trigger("reloadGrid");//上传完成后刷新表格
                            },
                            dataType:"json"
                        });
                        return postData;
                    }
                },
                {
                    closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                     var id= response.responseJSON.bannerid;
                     $.ajaxFileUpload({
                         url: "${pageContext.request.contextPath}/banner/updateImg",
                         type: "post",
                         data:{bannerId:id},
                         fileElementId:"url",
                         success:function (date) {
                             $("#bannertable").trigger("reloadGrid");
                         },
                         dataType:"json"
                     });
                        return postData;
                    }

                });
            //导出的时间
            $("#imgout").click(function () {
                $.ajax({
                    url:"${pageContext.request.contextPath}/banner/outpoi",
                    type:"post",
                    success:function (data) {
                        alert(data);
                    },
                    dataType:"json"
                })
            })
            //导入打开模态框事件
            $("#imginset").click(function () {
                $("#myModal").modal('show');
            })
            //导入提交事件
            $("#insetImg").click(function () {
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/banner/insertImg",
                    type:"post",
                    fileElementId:"imgchange",
                    success : function (data) {
                        alert(data);
                    },
                    dataType:"json"
                })
            })

        })
    </script>
</head>
<body>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>轮播图信息</a></li>
    <li><a href="javascript:void (0)" id="imgout">轮播图导出</a></li>
    <li><a href="javascript:void (0)" id="imginset">轮播图导入</a></li>
</ul>
<div class="panel">
    <table id="bannertable" border="1" style="text-align: center"></table>
    <div id="pager" style="height: 40px"></div>
</div>
<ul class="nav nav-tabs">
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">导入Excel</h4>
                </div>
                <div class="modal-body" id="mtk">
                    <form id="myform" class="form-horizontal" role="form" action="" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="imgchange" for="imgchange" class="col-sm-2 control-label">Excel</label>
                            <div class="col-sm-10">
                                <input type="file" name="imgchange" class="input-file uniform_on" id="imgchange" placeholder="请选择Excel">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="insetImg">导入</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</ul>
</body>
