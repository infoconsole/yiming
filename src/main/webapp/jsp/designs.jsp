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
        <form id="_search_designs">
            <div id="tb" style="padding:10px">
                <span>面料编号:</span>
                <input id="code" name="lcode" style="line-height:26px;border:1px solid #ccc">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true"
                   id="_search_des">查询设计</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                   id="_create_des">新增设计</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                   id="_delete_des">删除设计</a>
            </div>
        </form>
    </div>
    <div data-options="region:'center',border:false,plain:true">
        <table id="designs_dg" class="easyui-datagrid" style="width:100%;height:100%;padding: 20px;"
               data-options="singleSelect:true">
            <thead>
            <tr>
                <th data-options="field:'liningcode',halign:'center',width:'180',align:'left',sortable:true">
                    面料编号(面料二维码)
                </th>
                <th data-options="field:'designcode',hidden:true">设计编号</th>
                <th data-options="field:'designname',halign:'center',width:'650',align:'left',sortable:true">设计名称</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function () {
        if (init) {
            return;
        }
        init = true;

        //创建数据表格
        common.createDataGrid("designs_dg", "/queryliningsdesigns.do", null,
            "_search_designs", mainGridParam, false);

        //查询按钮监听
        $("#_search_des").click(function () {
            common.loadDateGrid("designs_dg"); //加载表格数据
        });

        //新建按钮
        $("#_create_des").click(function () {
            common.openWin("新增系列信息", "/showdesignsadd.do",
                1366, 768, "insertDesWin");
        });

        //删除按钮监听
        $("#_delete_des").click(function () {
            var _rowData = $("#designs_dg").datagrid("getSelected");
            if (_rowData == null || _rowData.length == 0) {
                messager.alert("请选择要操作的数据！");
                return;
            }
            $.messager.confirm("提示框", "您确认想要删除记录吗？", function (r) {
                if (r) {
                    ajaxUtil.post("/deleteliningsdesigns.do", {
                        designcode: _rowData.designcode,
                        liningcode: _rowData.liningcode
                    }, false, function (res) {
                        common.loadDateGrid("designs_dg");
                        messager.alert(res.messagecode);
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
