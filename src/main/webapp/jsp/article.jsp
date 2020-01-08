<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <script src="${pageContext.request.contextPath}/boot/js/jquery.form.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#articletable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/article/queryPage",
                    datatype : "json",
                    colNames : [ "id", "文章名称", "封面", "内容","创建时间","发布时间","状态","所属上师Id","修改"],
                    colModel : [
                        {name: "id"}, {name: "title",editable:true,editrules:{required:true}},
                        {
                            name: "img",editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                            formatter:function (date) {
                                return "<img width='100' height='50' src='"+date+"'></img>"
                            }
                        },
                        {name:"content",editable:true,editrules:{required:true}},
                        {name:"createDate",editable:true,edittype: "date",editrules:{required:true}},
                        {name:"publishDate",editable:true,edittype: "date",editrules:{required:true}},
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
                        {name:"guruId",editable:true,editrules:{required:true}},
                        {name:"option",
                            formatter:function (cellvalue, options, rowObject) {
                                var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                                return button;
                            }
                        }
                    ],
                    rowNum:3,
                    page:1,
                    rowList : [1, 2, 3, 5,7],
                    pager : '#pager',
                    sortname : 'id',
                    mtype:"post",
                    viewrecords : true,
                    sortorder : "desc",
                    caption : "文章信息",
                    autowidth: true,//只适应宽度
                    multiselect:true,//开启多选框
                    styleUI:"Bootstrap",
                    height:"300px",//宽度设定
                    editurl:"${pageContext.request.contextPath}/article/detet"
                });
            jQuery("#articletable").jqGrid('navGrid', '#pager',{add:false,edit:false,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"})
         //添加的开启模态框
          $("#addarticle").click(function () {
              $("#myform")[0].reset();
              $("#guruId").val("");
              $.ajax({
                  url : "${pageContext.request.contextPath}/article/queryGurn",
                  async : true,
                  type : "post",
                  // 成功后开启模态框
                  success : function (data) {
                            for(var i=0;i<data.length;i++){
                                var value = data[i].name;
                                var id=data[i].id;
                                $("#guruId").append("<option value="+id+">" + value + "<option>");
                            }
                      $('#myModal').modal('show');
                  },
                  dataType : "json"
              });
          })
            //添加/修改保存按钮触发事件
              $("#add").click(function () {
                  $.ajaxFileUpload({
                      url : "${pageContext.request.contextPath}/article/insertArticle",
                      async : true,
                      data:{
                          "id": $("#id").val(),
                          "title": $("#title").val(),
                          "content": $("#content").val(),
                          "createDate":$("#createDate").val(),
                          "publishDate":$("#publishDate").val(),
                          "status": $("#status").val(),
                          "guruId": $("#guruId").val()
                      },
                      type : "post",
                      fileElementId:"imgchange",
                      success : function (data) {
                          $("#myform")[0].reset();
                      },
                      dataType : "json",})
              })
        })
        // 点击修改时触发事件
        function update(id) {
            var data = $("#articletable").jqGrid("getRowData",id);
            $("#id").val(data.id),
            $("#title").val(data.title);
            $("#content").val(data.content);
            $("#createDate").val(data.createDate);
            $("#publishDate").val(data.publishDate);
            var option = "";
            if(data.status=="正常"){
                option += "<option selected value=\"1\">正常</option>";
                option += "<option value=\"2\">冻结</option>";
            }else{
                option += "<option value=\"1\">正常</option>";
                option += "<option selected value=\"2\">冻结</option>";
            }
            $("#status").html(option);
            //得到上师信息
            $.ajax({
                url : "${pageContext.request.contextPath}/article/queryGurn",
                async : true,
                type : "post",
                // 成功后开启模态框
                success : function (gurn) {
                    for(var i=0;i<gurn.length;i++){
                        var value = gurn[i].name;
                        var id=gurn[i].id;
                        if(id==data.guruId){
                            $("#guruId").append("<option selected value="+id+">" + value + "<option>");
                        }
                        $("#guruId").append("<option  value="+id+">" + value + "<option>");
                    }
                },
                dataType : "json"
            });
            $('#myModal').modal('show');
        }
    </script>
</head>
<body>
<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <button class="btn btn-primary btn-lg"  id="addarticle">添加文章</button>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">添加文章</h4>
                </div>
                <div class="modal-body" id="mtk">
                    <form id="myform" class="form-horizontal" role="form" action="" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <input type="hidden"  id="id">
                        </div>
                        <div class="form-group">
                            <label for="title" class="col-sm-2 control-label">文章标题</label>
                            <div class="col-sm-10">
                                <input type="text" name="title" class="form-control" id="title" placeholder="请输入标题">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="imgchange" for="imgchange" class="col-sm-2 control-label">文章封面</label>
                            <div class="col-sm-10">
                                    <input type="file" name="imgchange" class="input-file uniform_on" id="imgchange" placeholder="请选择封面">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="form-group">
                                <label for="content" class="col-sm-2 control-label" >文章内容</label>
                                <div class="col-sm-10">
                                    <textarea id="content" name="content" class="form-control" rows="3" placeholder="请选择内容"></textarea>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="createDate" class="col-sm-2 control-label">创建时间</label>
                            <div class="col-sm-10">
                                <input name="createDate" type="text" class="form-control" id="createDate" placeholder="请输入时间">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="publishDate" class="col-sm-2 control-label">发布时间</label>
                            <div class="col-sm-10">
                                <input name="publishDate" type="text" class="form-control" id="publishDate" placeholder="请输入时间">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="status" class="col-sm-2 control-label">文章状态</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="status" name="guruId">
                                    <option value="1">正常</option>
                                    <option value="2">冻结</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="guruId" class="col-sm-2 control-label">文章状态</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="guruId" >
                                    <option >请选择</option>
                                </select>

                            </div>
                        </div>


                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="add">保存</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</ul>
<div class="panel">
    <table id="articletable" border="1" style="text-align: center"></table>
    <div id="pager" style="height: 40px"></div>
</div>
</body>
