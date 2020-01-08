<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
</head>
<body>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 1000px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var option = {
            title: {
                // 标题名称
                text: '持名法洲用户注册趋势图',
            },
            // 工具提示
            tooltip: {},
            // 图例
            legend: {
                data:['男','女']
            },
            // X轴展示的内容  今天注册的用户数量  一周内注册的用户数量 一个月内注册的用户数量 一年内注册的用户数量
            xAxis: {
                data: ["今天注册的用户数量","一周内注册的用户数量","一个月内注册的用户数量","一年内注册的用户数量"]
            },
            // Y轴展示的内容 自适应的Y轴数据
            yAxis: {},
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contextPath}/user/queryTime",
        type:"get",
        dataType:"json",
        success:function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data.man
                },{
                    name: '女',
                    type: 'bar',
                    data: data.woman
                }]
            });
        }
    })
    </script>

</body>
