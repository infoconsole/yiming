<%--suppress ALL --%>
<%--
  Created by IntelliJ IDEA.
  User: oldflame
  Date: 2017/6/10
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
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
<div class="easyui-panel" title="Register" style="width:100%;padding:30px 60px;">
    <form id="companyff" method="post" enctype="multipart/form-data"
          accept="image/gif, image/jpeg,image/jpg, image/png">
        <div style="width: 60%;float: left">
            <table cellpadding="5">
                <tr>
                    <div style="margin-bottom:20px;width: 600px;">
                        <label class="label-top">公司:</label>
                        <input id="companyname" name="companyname" class="easyui-textbox"
                               data-options="prompt:'请输入公司...',required:true" style="width:100%;">
                    </div>
                    <div style="margin-bottom:20px;width: 600px;">
                        <label class="label-top">地址:</label>
                        <input id="address" name="address" class="easyui-textbox"
                               data-options="prompt:'请输入地址...',required:false" style="width:100%;">
                    </div>
                </tr>
                <tr>
                    <div style="margin-bottom:20px;width: 600px;">
                        <label class="label-top">电话:</label>
                        <input id="tel" name="tel" class="easyui-textbox"
                               data-options="prompt:'请输入电话...',required:false"
                               style="width:100%;">
                    </div>
                    <div style="margin-bottom:20px;width: 600px;">
                        <label class="label-top">首次登陆验证信息:</label>
                        <input id="securitycode" name="securitycode" class="easyui-textbox"
                               data-options="prompt:'请输入验证码...',required:false" style="width:100%;">
                    </div>
                </tr>
                <tr>
                    <div style="margin-bottom:20px;width: 600px;">
                        <label class="label-top" style="width:100%; display:block;">选择文件:</label>
                        <input id="file" name="file" class="easyui-filebox" style="width: 600px;"></div>
                </tr>
            </table>
        </div>
        <div style="width: 40%;float: left">
            <img src="/images/logo.png" style="margin: 50px;width: 300px;height: 300px;">
        </div>
        <table cellpadding="5">
            <tr>
                <div>
                    <label class="label-top">合作信息:</label>
                    <input id="joinhands" name="joinhands" class="easyui-textbox"
                           data-options="prompt:'请输入合作信息...',multiline:true,required:false"
                           style="height:100px;width: 100%">
                </div>
            </tr>
            <tr>
                <div>
                    <label class="label-top">制作工艺:</label>
                    <input id="workmanship" name="workmanship" class="easyui-textbox"
                           data-options="prompt:'请输入制作工艺...',multiline:true,required:false"
                           style="height:100px;width: 100%">
                </div>
            </tr>
            <tr>
                <div>
                    <label class="label-top">产品优势:</label>
                    <input id="advantage" name="advantage" class="easyui-textbox"
                           data-options="prompt:'请输入产品优势...',multiline:true,required:false"
                           style="height:100px;width: 100%">
                </div>
            </tr>
        </table>
    </form>
    <div style="text-align:center;padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitForm()">提交修改</a>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $.getJSON('getcompany.do', function (json) {
            $('#companyff').form('load', json);
        });
    });
    
    function submitForm() {
        $("#companyff").ajaxSubmit(
            {
                type: "post",  //提交方式
                url: "company.do", //请求url
                success: function (data) { //提交成功的回调函数
                    messager.msg(3, data);
                }
            }
        );
    }
</script>
</body>
</html>
