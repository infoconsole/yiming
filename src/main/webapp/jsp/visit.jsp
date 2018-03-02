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
        <form id="_search_visit">
            <div id="tb" style="padding:10px">
                <span>回访照类别:</span>
                <input id="visitname" name="visitname" style="line-height:26px;border:1px solid #ccc">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true"
                   id="_search_vi">查询类别</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                   id="_create_vi">新增类别</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true"
                   id="_update_vi">修改类别</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                   id="_delete_vi">删除类别</a>
            </div>
        </form>
    </div>
    <div data-options="region:'center',border:false,plain:true">
        <table id="visit_dg" class="easyui-datagrid" style="width:100%;height:100%;padding: 20px;"
               data-options="singleSelect:true">
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true,sortable:true">
                    id
                </th>
                <th data-options="field:'visitcode',hidden:true">回访照类别编号</th>
                <th data-options="field:'visitname',halign:'center',width:'650',align:'left',sortable:true">回访照类别</th>
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
        common.createDataGrid("visit_dg", "/queryvisits.do", null,
            "_search_visit", mainGridParam, false);

        //查询按钮监听visit
        $("#_search_vi").click(function () {
            common.loadDateGrid("visit_dg"); //加载表格数据
        });

        //新建按钮
        $("#_create_vi").click(function () {
            common.openWin("新增回访照信息", "/showvisitadd.do",
                1366, 768, "insertViWin");
        });

        //新建按钮
        $("#_update_vi").click(function () {

            var _rowData = $("#visit_dg").datagrid("getSelected");
            if (_rowData == null || _rowData.length == 0) {
                messager.alert("请选择要操作的数据！");
                return;
            }
            common.openWin("更新回访照信息", "/showvisitupdate.do",
                1366, 768, "updateViWin", _rowData);
        });

        //删除按钮监听
        $("#_delete_vi").click(function () {
            var _rowData = $("#visit_dg").datagrid("getSelected");
            if (_rowData == null || _rowData.length == 0) {
                messager.alert("请选择要操作的数据！");
                return;
            }
            $.messager.confirm("提示框", "您确认想要删除记录吗？", function (r) {
                if (r) {
                    ajaxUtil.post("/deletevisit.do", {
                        visitcode: _rowData.visitcode
                    }, false, function (res) {
                        common.loadDateGrid("visit_dg");
                        messager.msg(3, res.messagecode);
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
