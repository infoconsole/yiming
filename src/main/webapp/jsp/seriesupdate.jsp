<%--
  Created by IntelliJ IDEA.
  User: oldflame
  Date: 2017/6/12
  Time: 23:59
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
<body>
<div class="easyui-layout" data-options="fit:true">
    <form id="seriesfff" method="post">
        <div data-options="region:'north'" style="padding:10px;height: 390px">
            <div style="margin-bottom:20px;">
                <label class="label-top">系列编号（二维码）:</label>
                <input type="hidden" id="id" name="id" value="${Series.id}">
                <input id="seriescode" name="seriescode" class="easyui-textbox"
                       data-options="required:true,editable:false" style="width:100%;"
                       value="${Series.seriescode}">
            </div>
            <div style="margin-bottom:20px;">
                <label class="label-top">系列名称:</label>
                <input id="seriesname" name="seriesname" class="easyui-textbox"
                       data-options="prompt:'请输入系列名称...',required:false" style="width:100%;"
                       value="${Series.seriesname}">
            </div>
            <div>
                <label class="label-top">系列介绍信息:</label>
                <input id="seriescontent" name="seriescontent" class="easyui-textbox"
                       data-options="prompt:'请输入系列介绍...',multiline:true,required:false"
                       style="height:150px;width: 100%" value="${Series.seriescontent}">
            </div>
        </div>
    </form>
    <div data-options="region:'south',border:false" style="text-align:right;padding:5px;">
        <a id="update_ser_ok" class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
           href="javascript:void(0)" style="width:80px">保存</a>
        <a id="update_ser_cancel" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
           href="javascript:void(0)" style="width:80px">取消</a>
    </div>
</div>
<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function () {
        if (init) return;
        init = true;
    };

    $("#update_ser_ok").click(function () {
        $("#seriesfff").form("submit", {
            url: "/updateseries.do",
            success: function (data) {
                if (data == "success") {
                    $("#series_dg").datagrid("reload");
                    messager.msg(3, "保存成功");
                    common.closeWin("insertUpdateSerWin");
                } else {
                    messager.msg(3, "保存失败:" + data);
                }
            }
        });
    });

    //取消按钮监听
    $("#update_ser_cancel").click(function () {
        common.closeWin("insertUpdateSerWin");
    });
</script>
</body>
</html>
