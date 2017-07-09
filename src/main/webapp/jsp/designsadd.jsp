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
    <form id="liningff" method="post">
        <div data-options="region:'north'" style="padding:10px;height: 150px">
            <div style="margin-bottom:10px;">
                <label class="label-top">面料编号（二维码）</label>
                <input id="_lining_code" name="liningcode" class="easyui-textbox" style="width:100%;">
            </div>
            <div style="margin-bottom:10px;">
                <label class="label-top">设计名称</label>
                <input id="_design_name" name="designname" class="easyui-textbox"
                       data-options="prompt:'请输入面料编号...',required:true" style="width:100%;">
            </div>
        </div>
        <div data-options="region:'center'" style="padding:10px;height: 510px">
            <div title="设计效果图 &nbsp;&nbsp;">
                <div id="_sub_center_sj" style="border-top: 0px"
                     data-options="region:'center',noheader:true,border:false">
                    <div id="sj_menu" style="width: 100%;">
                        <table width="100%">
                            <tr>
                                <td width="82%">
                                    <p>&nbsp;&nbsp;</p>
                                </td>
                                <td width="6%">
                                    <a href="javascript:void(0)"
                                       class="easyui-linkbutton"
                                       data-options="iconCls:'icon-add',text:'添加'" id="sj_add">添加</a>
                                </td>
                                <td width="6%">
                                    <a href="javascript:void(0)"
                                       class="easyui-linkbutton"
                                       data-options="iconCls:'icon-edit',text:'添加'" id="sj_edit">修改</a>
                                </td>
                                <td width="6%">
                                    <a href="javascript:void(0)"
                                       class="easyui-linkbutton "
                                       data-options="iconCls:'icon-remove',text:'删除'" id="sj_delete">删除</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table id="sj_dg" class="easyui-datagrid" style="width:100%;height:440px;padding: 20px;"
                           data-options="singleSelect:true">
                        <thead>
                        <tr>
                            <th data-options="field:'id',hidden:true">
                                图片id号
                            </th>
                            <th data-options="field:'name',halign:'center',width:'250',align:'left',sortable:true">
                                图片名称
                            </th>
                            <th data-options="field:'type',width:'250',required:true" editor="{type:'combobox',options:{valueField:'id',textField:'text',
                            data:[{id:'设计效果图',text:'设计效果图'},{id:'空间效果图',text:'空间效果图'},{id:'软装搭配设计',text:'软装搭配设计'},{id:'客户回访照',text:'客户回访照'}]}}">
                                图片类型
                            </th>
                            <th data-options="field:'url',hidden:true">
                                图片url
                            </th>
                            <th data-options="field:'urlfix',hidden:true">
                                图片url
                            </th>
                            <th data-options="field:'content',width:600" editor="{type:'textbox'}">图片描述</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </form>
    <form id="sj_formUpload" method="post" enctype="multipart/form-data"
          accept="image/gif, image/jpeg,image/jpg, image/png">
        <input id="sj_fileUpload" type="file" name="upload" style="display: none"/>
    </form>
    <form id="xg_formUpload" method="post" enctype="multipart/form-data"
          accept="image/gif, image/jpeg,image/jpg, image/png">
        <input id="xg_fileUpload" type="file" name="upload" style="display: none"/>
    </form>
    <div data-options="region:'south',border:false" style="text-align:right;padding:5px;">
        <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
           id="insert_des_ok" style="width:80px">保存</a>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
           id="insert_des_cancel" style="width:80px">取消</a>
    </div>
</div>
<script type="text/javascript">
    var init = false;
    var locksj = null;
    var lockxg = null;
    $.parser.onComplete = function () {
        if (init) return;
        init = true;

        $("#sj_add").click(function () {
            $("#sj_fileUpload").trigger('click');
            $("#sj_fileUpload").on('change', function () {
                var filePathStr = $(this).val();
                if (!(/[.]/.exec(filePathStr))) {
                    messager.msg(3, "文件格式不正确,请重新上传");
                    return;
                }
                var _fileName = filePathStr.replace(/.*(\/|\\)/, "");
                $("#sj_formUpload").ajaxSubmit(
                    {
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        type: 'post', // 提交方式 get/post
                        url: '/fileupload.do', // 需要提交的 url
                        success: function (data) { // data 保存提交后返回的数据，一般为 json 数据
                            if (data.status == 1) {
                                var dgdata = data.rows;
                                dgdata.name = _fileName;
                                dgdata.content = _fileName.substring(0, _fileName.indexOf("."));
                                //先保存
                                if (locksj != null) {
                                    $("#sj_dg").datagrid("endEdit", locksj);
                                }
                                common.insertRow("sj_dg", dgdata);
                                //打开第一行的编辑
                                locksj = 0;
                                $("#sj_dg").datagrid("beginEdit", locksj);

                                $('#sj_fileUpload').replaceWith('<input id="sj_fileUpload" type="file" name="upload" style="display: none" />');
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
//
//        $("#xg_add").click(function () {
//            $("#xg_fileUpload").trigger('click');
//            $("#xg_fileUpload").on('change', function () {
//                var filePathStr = $(this).val();
//                if (!(/[.]/.exec(filePathStr))) {
//                    messager.msg(3, "文件格式不正确,请重新上传");
//                    return;
//                }
//                var _fileName = filePathStr.replace(/.*(\/|\\)/, "");
//                $("#xg_formUpload").ajaxSubmit(
//                    {
//                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
//                        type: 'post', // 提交方式 get/post
//                        url: '/fileupload.do', // 需要提交的 url
//                        success: function (data) { // data 保存提交后返回的数据，一般为 json 数据
//                            if (data.status == 1) {
//                                var dgdata = data.rows;
//                                dgdata.name = _fileName;
//                                dgdata.content = _fileName.substring(0, _fileName.indexOf("."));
//                                common.insertRow("xg_dg", dgdata);
//                                $('#xg_fileUpload').replaceWith('<input id="xg_fileUpload" type="file" name="upload" style="display: none" />');
//                            } else {
//                                messager.msg(3, data.messagecode);
//                            }
//                        },
//                        error: function (data) {
//                            messager.msg(3, "上传失败");
//                            console.log(data);
//                        }
//                    }
//                );
//            });
//        });


        $("#sj_edit").click(function () {
                var _rowData = $("#sj_dg").datagrid("getSelected");
                if (_rowData == null || _rowData == undefined) {
                    messager.msg(3);// 无数据时提示窗
                    return;
                }
                if (locksj != null) {
                    $("#sj_dg").datagrid("endEdit", locksj);
                }
                locksj = $("#sj_dg").datagrid('getRowIndex', _rowData);
                $("#sj_dg").datagrid("beginEdit", locksj);
            }
        );

//        $("#xg_edit").click(function () {
//                var _rowData = $("#xg_dg").datagrid("getSelected");
//                if (_rowData == null || _rowData == undefined) {
//                    messager.msg(3);// 无数据时提示窗
//                    return;
//                }
//                if (lockxg != null) {
//                    $("#xg_dg").datagrid("endEdit", lockxg);
//                }
//                lockxg = $("#xg_dg").datagrid('getRowIndex', _rowData);
//                $("#xg_dg").datagrid("beginEdit", lockxg);
//            }
//        );

        $("#sj_delete").click(
            function () {
                var rows = $("#sj_dg").datagrid("getSelected");
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
                        var index = $("#sj_dg").datagrid("getRowIndex", rows);
                        $("#sj_dg").datagrid("deleteRow", index);
                    }
                });
            });

//        $("#xg_delete").click(
//            function () {
//                var rows = $("#xg_dg").datagrid("getSelected");
//                if (rows == null || rows.length == 0) {
//                    messager.msg(3, "请选择要删除的附件信息！");
//                    return;
//                }
//                $.messager.confirm("提示信息", "确定要删除该图片吗", function (r) {
//                    if (r) {
//                        ajaxUtil.post("/deleteFile.do", {urlfix: rows.urlfix}, false, function (data) {
//                            if (data.status == 1) {
//                                messager.msg(3, "删除附件信息成功");
//                            }
//                        });
//                        var index = $("#xg_dg").datagrid("getRowIndex", rows);
//                        $("#xg_dg").datagrid("deleteRow", index);
//                    }
//                });
//            });

        $("#insert_des_ok").click(function () {
                if (locksj != null) {
                    $("#sj_dg").datagrid("endEdit", locksj);
                }
//                if (lockxg != null) {
//                    $("#xg_dg").datagrid("endEdit", lockxg);
//                }
                $.messager.confirm("提示信息", "确定要保存信息码", function (r) {
                    if (r) {
                        debugger;
                        var sjRowsData = $("#sj_dg").datagrid('getRows');
                        for(var i=0;i<sjRowsData.length;i++){
                            if(sjRowsData[i].type=="") {
                                messager.msg(3, "请编辑选择图片类型");
                                return false;
                            }
                        }

//                        var xgRowsData = $("#xg_dg").datagrid('getRows');
                        var rdata = {
                            liningcode: $("#_lining_code").textbox("getValue"),
                            designname: $("#_design_name").textbox("getValue"),
                            sjlist: JSON.stringify(sjRowsData)
//                            xglist: JSON.stringify(xgRowsData)
                        }
                        ajaxUtil.post("/savedesigns.do", rdata, false, function (data) {
                            if (data.status == 1) {
                                common.loadDateGrid("designs_dg")//刷新主页面到第一页
                                common.closeWin("insertDesWin");
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
        $("#insert_des_cancel").click(function () {
            common.closeWin("insertDesWin");
        });
    }

</script>
</body>
</html>
