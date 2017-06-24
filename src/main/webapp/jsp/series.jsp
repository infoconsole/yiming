<%--
  Created by IntelliJ IDEA.
  User: oldflame
  Date: 2017/6/11
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>一鸣兰客厅</title>
    <script type="text/javascript" src="easyui-theme/jquery.min.js"></script>
    <script type="text/javascript" src="easyui-theme/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui-theme/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
    <script type="text/javascript" src="js/messager.js"></script>
    <script type="text/javascript" src="js/system.js"></script>
    <script type="text/javascript" src="js/ajaxUtil.js"></script>
    <script type="text/javascript" src="js/jquery.edatagrid.js"></script>
    <link rel="stylesheet" type="text/css" href="easyui-theme/themes/insdep/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui-theme/themes/insdep/easyui.css">

</head>
<body class="easyui-layout">
<div id="cc" class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north',border:false" style="height:60px">
        <form id="_search_series">
            <div id="tb" style="padding:10px">
                <span>系列编号:</span>
                <input id="code" name="scode" style="line-height:26px;border:1px solid #ccc">
                <span>系列名称:</span>
                <input id="name" name="sname" style="line-height:26px;border:1px solid #ccc">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" id="_search">查询面料</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" id="_create_series">新增系列</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" id="_create_lining">新增面料</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" id="_delete">删除面料</a>
            </div>
        </form>
    </div>
    <div data-options="region:'center',border:false,plain:true">
        <table id="series_dg" class="easyui-datagrid" style="width:100%;height:100%;padding: 20px;"
               data-options="singleSelect:true">
            <thead>
            <tr>
                <th data-options="field:'seriescode',halign:'center',width:'180',align:'left',sortable:true" >系列编号(系列二维码)</th>
                <th data-options="field:'seriesname',halign:'center',width:'180',align:'left',sortable:true" >系列名称（作为显示使用）</th>
                <th data-options="field:'seriescontent',halign:'center',width:'650',align:'left',sortable:true" >系列描述（作为显示使用）</th>
                <th data-options="field:'liningcode',halign:'center',width:'180',align:'left',sortable:true" >面料编号(面料二维码)</th>
                <th data-options="field:'liningname',halign:'center',width:'150',align:'left',sortable:true" >面料名称</th>
                <th data-options="field:'liningcolor',halign:'center',width:'150',align:'left',sortable:true" >面料颜色</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function (context) {
        if (init) {
            return;
        }
        init = true;
        //创建数据表格
        common.createDataGrid("series_dg", "/queryserieslinings.do",null,
            "_search_series" , mainGridParam, false);

        //查询按钮监听
        $("#_search").click(function () {
            common.loadDateGrid("series_dg"); //加载表格数据
        });

        //查询按钮监听
        $("#_create_series").click(function () {
            common.openWin("新增系列信息", "/showseriesadd.do",
                800, 500, "insertSerWin");
        });
        //查询按钮监听
        $("#_create_lining").click(function () {
            common.openWin("新增面料信息", "/showliningsadd.do",
                800, 500, "insertLinWin");
        });

        //删除按钮监听
        $("#_delete").click(function () {
            var _rowData=$("#series_dg").datagrid("getSelected");
            if(_rowData == null || _rowData.length == 0){
                messager.msg(3, "请选择要操作的数据！");
                return;
            }
            $.messager.confirm("提示框","您确认想要删除记录吗？",function(r){
                if (r){
                    ajaxUtil.post("/deleteserieslinings.do", {seriescode:_rowData.seriescode,
                        liningcode:_rowData.liningcode
                    },false, function (data){
                        if (data.status == 1) {
                            common.loadDateGrid("series_dg");
                            messager.msg(3, "删除成功");
                        }else{
                            common.loadDateGrid("series_dg");
                            messager.msg(3, "删除失败");
                        }
                    });
                }
            });
        });
    };

    var mainGridParam = {
        pagination: false,
        singleSelect: true,
        checkOnSelect: true,
        selectOnCheck: true,
        pageSize: 50,
        pageList: [10, 50, 100],
        method: "POST"
    }
</script>
</body>
</html>
