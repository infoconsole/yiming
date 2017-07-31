<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
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


    <script type="text/javascript">
        function opentitle(title) {
            if ($("#tt").tabs("exists", title)) {
                $("#tt").tabs("select", title);
            } else {
                if (title == "公司信息维护") {
                    $('#tt').tabs('add', {
                        title: '公司信息维护',
                        href: 'jsp/company.jsp',
                        closable: true
                    });
                } else if (title == "窗帘系列维护") {
                    $('#tt').tabs('add', {
                        title: '窗帘系列维护',
                        href: 'jsp/series.jsp',
                        closable: true
                    });
                } else if (title == "设计款式维护") {
                    $('#tt').tabs('add', {
                        title: '设计款式维护',
                        href: 'jsp/designs.jsp',
                        closable: true
                    });
                } else if (title == "回访照维护") {
                    $('#tt').tabs('add', {
                        title: '回访照维护',
                        href: 'jsp/visit.jsp',
                        closable: true
                    });
                }
            }
        }

    </script>
</head>

<body class="easyui-layout">
<div id="cc" class="easyui-layout" style="width:100%;height:100%;padding-bottom: 45px;">
    <div data-options="region:'north',border:false" style="height:60px;background-color: black">
        <img alt="logo" src="images/logo.png" style="padding: 0px;height: 55px">
    </div>
    <div data-options="region:'west',title:'操作菜单',split:true" style="width:200px;">
        <div id="aa" class="easyui-accordion" data-options="fit:true,border:false">
            <div title="图片维护管理" data-options="iconCls:'icon-save',selected:true"
                 style="overflow:auto;padding:10px;">
                <a id="btn1" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"
                   onclick="opentitle('公司信息维护')">公司信息维护</a>
                <br/>
                <a id="btn2" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"
                   onclick="opentitle('窗帘系列维护')">窗帘系列维护</a>
                <br/>
                <a id="btn3" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"
                   onclick="opentitle('设计款式维护')">设计款式维护</a>
                <br/>
                <a id="btn4" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true"
                   onclick="opentitle('回访照维护')">回访照维护</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center',border:false,plain:true">
        <div id="tt" class="easyui-tabs" fit=true>
            <div title="首页" style="text-align: center;font-size: 24px;margin: 0px;padding: 0px;">
                <div data-options="region:'north',border:false" style="height:60px">
                    <form id="_tt_ff">
                        <div id="tb" style="padding:10px">
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                               plain="true"
                               id="_z_pic">整理图片</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"
                               plain="true"
                               id="_down_db">下载数据</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div style="height: 40px;text-align: center;font-size: 12px;padding: 0px;">
    <p>版权所有,一鸣兰客厅</p>
</div>
<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function () {
        if (init) {
            return;
        }
        init = true;
        $("#_down_db").click(function () {
            window.location.href = '/downdb.do';
        });

        $("#_z_pic").click(function () {
            ajaxUtil.post("/zpic.do", null, false, function (data) {
                if (data.status == 1) {
                    messager.msg(3, "图片整理完成");
                }
            });
        });
    }


</script>
</body>
</html>