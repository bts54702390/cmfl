<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <script type="text/javascript">
        $(function () {
            $("#AlbumTable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/album/queryPage",
                    datatype : "json",
                    colNames : [ "id", "标题", "评分", "作者","播音","集数","描述","封面","创作时间"],
                    colModel : [
                        {name: "id",width :350},
                        {name: "title",editable:true,editrules:{required:true}},
                        {name: "score",editable:true,editrules:{required:true}},
                        {name:"author",editable:true,editrules:{required:true}},
                        {name:"broadcast",editable:true,editrules:{required:true}},
                        {name:"count"},
                        {name:"descys",editrules:{required:true},editable:true},
                        {
                            name: "status",editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                            formatter:function (date) {
                                return "<img width='100' height='50' src='"+date+"'></img>"
                            }
                        },
                        {name:"createDate",editable:true,edittype: "date",editrules:{required:true}}
                    ],
                    rowNum:3,
                    page:1,
                    rowList : [1, 2, 3, 5,7],
                    pager : '#pager',
                    sortname : 'id',
                    mtype : "post",
                    viewrecords : true,
                    sortorder : "desc",
                    caption : "专辑",
                    autowidth: true,//只适应宽度
                    multiselect:true,//开启多选框
                    styleUI:"Bootstrap",
                    height:"300px",//宽度设定
                    subGrid : true,
                    subGridRowExpanded : function(subgrid_id, row_id) {
                        addzj(subgrid_id, row_id)
                    },
                        editurl:"${pageContext.request.contextPath}/album/iud"
                });
            jQuery("#AlbumTable").jqGrid('navGrid', '#pager',{add:true,edit:true,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"},
                {
                    closeAfterEdit: true,
                    afterSubmit:function (response,postData) {
                      var AlbumId=  response.responseJSON.albumid;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/updateImg",
                            type: "post",
                            data:{albumId:AlbumId},
                            fileElementId:"status",
                            success:function (date) {
                                $("#AlbumTable").trigger("reloadGrid");//上传完成后刷新表格
                            },
                            dataType:"json"
                        });
                        return postData;
                    }
                },
                {
                    closeAfterAdd: true,
                    afterSubmit:function (response,postData) {
                        var AlbumId=  response.responseJSON.albumid;
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/album/updateImg",
                            type: "post",
                            data:{albumId:AlbumId},
                            fileElementId:"status",
                            success:function (date) {
                                $("#AlbumTable").trigger("reloadGrid");//上传完成后刷新表格
                            },
                            dataType:"json"
                        });
                        return postData;
                    }
                 }
                );
             //章节表格生成
            function addzj(subgrid_id, row_id) {
                var subgrid_table_id, pager_id;
                var albumId=row_id;
                sId = subgrid_id + "_t";//子表格的id
                pagerId = "p_" + subgrid_table_id;//子菜单的id
                //创建子表格
                $("#"+subgrid_id).html("<table id='" +sId+ "'border='1' style='text-align: center'></table><div id='" +pagerId+ "'style='height: 40px'></div>");
                $("#"+sId).jqGrid(
                    {
                        url : "${pageContext.request.contextPath}/chapter/qureyPage?albumId="+albumId,
                        datatype : "json",
                        colNames : [ "id", "标题", "音频", "音频大小","音频时间","录制时间","所属章节id"],
                        colModel : [
                            {name: "id",width :350},
                            {name: "title",editable:true,editrules:{required:true}},
                            {
                                name: "url",editrules:{required:true},edittype:'file',editable:true,editoptions:{enctype:"multipart/form-data"},
                                formatter:function (date) {
                                    return "<audio controls loop preload='auto'>"+"<source src='"+date+"' type='audio/mpeg'></source>"+"<source src='"+date+"' type='audio/ogg'></source></audio>"
                                    //<img width='100' height='50' src='"+date+"'></img>
                                }
                            },
                            {name:"size"},
                            {name:"time"},
                            {name:"createTime",editable:true,edittype: "date",editrules:{required:true}},
                            {name:"albumId"},
                        ],
                        rowNum:2,
                        page:1,
                        rowList : [1, 2, 3, 5,7],
                        pager : pagerId,
                        sortname : 'id',
                        mtype : "get",
                        viewrecords : true,
                        sortorder : "desc",
                        caption : "章节",
                        autowidth: true,//只适应宽度
                        multiselect:true,//开启多选框
                        styleUI:"Bootstrap",
                        height:"300px",//宽度设定
                        editurl:"${pageContext.request.contextPath}/chapter/img?albumId="+albumId
                    });
                jQuery("#"+sId).jqGrid('navGrid', "#"+pagerId,{add:true,edit:true,del:true,refresh:true,edittext:"修改",addtext:"添加",deltext:"删除"},
                    {
                        closeAfterEdit: true,
                        afterSubmit:function (response,postData) {
                            var chapterid=  response.responseJSON.chapterId;
                            console.log(chapterid);
                            $.ajaxFileUpload({
                                url: "${pageContext.request.contextPath}/chapter/updateImg",
                                type: "post",
                                data:{chaptersd:chapterid},
                                fileElementId:"url",
                                success:function (date) {
                                    $("#"+sId).trigger("reloadGrid");//上传完成后刷新表格
                                },
                                dataType:"json"
                            });
                            return postData;}
                    },
                    {
                        closeAfterAdd: true,
                        afterSubmit:function (response,postData) {
                            var chapterid=  response.responseJSON.chapterId;
                            console.log(chapterid);
                            $.ajaxFileUpload({
                                url: "${pageContext.request.contextPath}/chapter/updateImg",
                                type: "post",
                                data:{chaptersd:chapterid},
                                fileElementId:"url",
                                success:function (date) {
                                    $("#"+sId).trigger("reloadGrid");//上传完成后刷新表格
                                },
                                dataType:"json"
                            });
                            return postData;}
                    }
                    )
            }
        })
    </script>
</head>
<body>
<div class="page-header">
    <h4>专辑管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a>专辑图信息</a></li>
</ul>
<div class="panel">
    <table id="AlbumTable" border="1" style="text-align: center"></table>
    <div id="pager" style="height: 40px"></div>
</div>
</body>