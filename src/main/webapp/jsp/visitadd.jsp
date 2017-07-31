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
    <form id="visitff" method="post">
        <div data-options="region:'north'" style="padding:10px;height: 150px">
            <div style="margin-bottom:10px;">
                <label class="label-top">类别名称</label>
                <input id="_visit_name" name="visitname" class="easyui-textbox"
                       data-options="prompt:'请输入类别名称...',required:true" style="width:100%;">
            </div>
        </div>
        <div data-options="region:'center'" style="padding:10px;height: 510px">
            <div title="回访照图片 &nbsp;&nbsp;">
                <div id="_sub_center_visit" style="border-top: 0px"
                     data-options="region:'center',noheader:true,border:false">
                    <div id="visit_menu" style="width: 100%;">
                        <table width="100%">
                            <tr>
                                <td width="82%">
                                    <p>&nbsp;&nbsp;</p>
                                </td>
                                <td width="6%">
                                    <a href="javascript:void(0)"
                                       class="easyui-linkbutton"
                                       data-options="iconCls:'icon-add',text:'添加'" id="vi_add">添加</a>
                                </td>
                                <td width="6%">
                                    <a href="javascript:void(0)"
                                       class="easyui-linkbutton "
                                       data-options="iconCls:'icon-remove',text:'删除'" id="vi_delete">删除</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table id="vi_dg" class="easyui-datagrid" style="width:100%;height:440px;padding: 20px;"
                           data-options="singleSelect:true">
                        <thead>
                        <tr>
                            <th data-options="field:'id',hidden:true">
                                图片id号
                            </th>
                            <th data-options="field:'name',halign:'center',width:'250',align:'left',sortable:true">
                                图片名称
                            </th>
                            <th data-options="field:'url',hidden:true">
                                图片url
                            </th>
                            <th data-options="field:'urlfix',hidden:true">
                                图片url
                            </th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </form>
    <form id="vi_formUpload" method="post" enctype="multipart/form-data"
          accept="image/gif, image/jpeg,image/jpg, image/png">
        <input id="vi_fileUpload" type="file" name="upload" style="display: none"/>
    </form>
    <div data-options="region:'south',border:false" style="text-align:right;padding:5px;">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           id="insert_vi_ok" style="width:80px">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           id="insert_vi_cancel" style="width:80px">取消</a>
    </div>
</div>
<script type="text/javascript">
    var init = false;
    $.parser.onComplete = function () {
        if (init) return;
        init = true;

        $("#vi_add").click(function () {
            $("#vi_fileUpload").trigger('click');
            $("#vi_fileUpload").on('change', function () {
                var filePathStr = $(this).val();
                if (!(/[.]/.exec(filePathStr))) {
                    messager.msg(3, "文件格式不正确,请重新上传");
                    return;
                }
                var _fileName = filePathStr.replace(/.*(\/|\\)/, "");
                $("#vi_formUpload").ajaxSubmit(
                    {
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        type: 'post', // 提交方式 get/post
                        url: '/fileupload.do', // 需要提交的 url
                        success: function (data) { // data 保存提交后返回的数据，一般为 json 数据
                            if (data.status == 1) {
                                var dgdata = data.rows;
                                dgdata.name = _fileName;
                                common.insertRow("vi_dg", dgdata);

                                $('#vi_fileUpload').replaceWith('<input id="vi_fileUpload" type="file" name="upload" style="display: none" />');
                            } else {
                                messager.msg(3, data.messagecode);
                            }
                        },
                        error: function (data) {
                            messager.msg(3, "上传失败");
                            console.log(data);
                        }
                    }
                );
            });
        });


        $("#vi_delete").click(
            function () {
                var rows = $("#vi_dg").datagrid("getSelected");
                if (rows == null || rows.length == 0) {
                    messager.msg(3, "请选择要删除的附件信息！");
                    return;
                }
                $.messager.confirm("提示信息", "确定要删除该图片吗", function (r) {
                    if (r) {
                        ajaxUtil.post("/deleteFile.do", {urlfix: rows.urlfix}, false, function (data) {
                            if (data.status == 1) {
                                messager.msg(3, "删除附件信息成功");
                            }
                        });
                        var index = $("#vi_dg").datagrid("getRowIndex", rows);
                        $("#vi_dg").datagrid("deleteRow", index);
                    }
                });
            });


        $("#insert_vi_ok").click(function () {
                $.messager.confirm("提示信息", "确定要保存信息码", function (r) {
                    if (r) {
                        debugger;
                        var sjRowsData = $("#vi_dg").datagrid('getRows');
                        var rdata = {
                            visitname: $("#_visit_name").textbox("getValue"),
                            visitlist: JSON.stringify(sjRowsData)
                        }
                        ajaxUtil.post("/savevisits.do", rdata, false, function (data) {
                            if (data.status == 1) {
                                common.loadDateGrid("visit_dg")//刷新主页面到第一页
                                common.closeWin("insertViWin");
                                messager.msg(3, "保存信息成功");
                            } else {
                                messager.msg(3, data.messagecode);
                            }
                        });
                    }
                });
            }
        );

        //取消按钮监听
        $("#insert_vi_cancel").click(function () {
            common.closeWin("insertViWin");
        });
    }

</script>
</body>
</html>
