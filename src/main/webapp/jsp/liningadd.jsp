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
    <link rel="stylesheet" type="text/css" href="css/common.css">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <form id="liningff" method="post" enctype="multipart/form-data"
          accept="image/gif, image/jpeg,image/jpg, image/png">
        <div data-options="region:'north'" style="padding:10px;height: 390px">
            <div style="margin-bottom:20px;">
                <label class="label-top">系列编号（二维码）:</label>
                <input id="_series_code" name="seriescode" class="easyui-combobox" style="width:100%; height:30px;">
            </div>
            <div style="margin-bottom:20px;">
                <label class="label-top">面料编号（二维码）:</label>
                <input id="_lining_code" name="liningcode" class="easyui-textbox"
                       data-options="prompt:'请输入面料编号...',required:true" style="width:100%;">
            </div>
            <div style="margin-bottom:20px;">
                <label class="label-top">面料名称:</label>
                <input id="_lining_name" name="liningname" class="easyui-textbox"
                       data-options="prompt:'请输入面料名称...',required:false" style="width:100%;">
            </div>
            <div style="margin-bottom:20px;">
                <label class="label-top">面料颜色:</label>
                <input id="_lining_color" name="liningcolor" class="easyui-textbox"
                       data-options="prompt:'请输入面料颜色...',required:false" style="width:100%;">
            </div>
            <tr>
                <div style="margin-bottom:20px;width: 45%;">
                    <label class="label-top" style="width:100%; display:block;">色卡图片:</label>
                    <input id="_lin_file" name="file" class="easyui-filebox" style="width:300px"></div>
            </tr>
        </div>
    </form>

    <div data-options="region:'south',border:false" style="text-align:right;padding:5px;">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           id="insert_lin_ok" style="width:80px">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           id="insert_lin_cancel" style="width:80px">取消</a>
    </div>
</div>
<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function () {
        if (init) return;
        init = true;

        system.loadComboItems("_series_code", "/getseriescombox.do", null, null, false);

        $("#insert_lin_ok").click(function () {
            $("#liningff").ajaxSubmit(
                {
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    type: "post",  //提交方式
                    url: "/addlining.do", //请求url
                    success: function (data) { //提交成功的回调函数
                        debugger;
                        if (data == "success") {
                            common.loadDateGrid("series_dg")//刷新主页面到第一页
                            common.closeWin("insertLinWin");
                        } else {
                            messager.msg(3, "保存失败："+data);
                        }
                    }
                }
            );
        });

        //取消按钮监听
        $("#insert_lin_cancel").click(function () {
            common.closeWin("insertLinWin");
        });
    }
</script>
</body>
</html>
