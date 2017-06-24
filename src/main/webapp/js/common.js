var common = {

    /**
     * 当前表格中新增一条记录
     */
    insertRow: function (_domId, _values) {
        $("#" + _domId).datagrid("insertRow", {
            index: 0,	// 索引从0开始
            row: _values
        });
        var crows = $("#" + _domId).datagrid("getRows");
        $("#" + _domId).datagrid("loadData", crows);
    },

    /**
     * 更新表格当前选中行
     */
    updateCurrentRow: function (_domId, _values) {
        var datagrid = $("#" + _domId);
        if (datagrid) {
            var rowData = datagrid.datagrid("getSelected");
            if (rowData) {
                var index = datagrid.datagrid("getRowIndex", rowData);
                rowData = this.refreshData(rowData, _values);
                datagrid.datagrid("updateRow", {
                    index: index,
                    row: rowData
                });
                datagrid.datagrid("refreshRow", index);
            }
        }
    },

    /**
     * 更新表格数据行
     */
    updateRowData: function (_domId, _rowData, _values) {
        var datagrid = $("#" + _domId);
        if (datagrid) {
            if (_rowData) {
                var index = datagrid.datagrid("getRowIndex", _rowData);
                _rowData = this.refreshData(_rowData, _values);
                datagrid.datagrid("updateRow", {
                    index: index,
                    row: _rowData
                });
                datagrid.datagrid("refreshRow", index);
            }
        }
    },

    /**
     * 删除表格数据行
     */
    deleteRowData: function (_domId, _rowData) {
        var datagrid = $("#" + _domId);
        if (datagrid) {
            if (_rowData) {
                var index = datagrid.datagrid("getRowIndex", _rowData);
                datagrid.datagrid("deleteRow", index);
            }
        }
    },

    /**
     * 更新表格数据行
     */
    updateRowIndex: function (_domId, _index, _values) {
        var datagrid = $("#" + _domId);
        if (datagrid) {
            //var rowData = datagrid.datagrid("selectRow", _index);
            //if (rowData) {
            //rowData = this.refreshData(rowData, _values);
            datagrid.datagrid("updateRow", {
                index: _index,
                row: _values
            });
            datagrid.datagrid("refreshRow", _index);
        }
        //}
    },

    /**
     * 刷新数据集
     */
    refreshData: function (_source, _values) {
        if (_source && _values) {
            for (var v in _values) {
                _source[v] = _values[v];
            }
        }
        return _source;
    },

    /**
     * 打开窗口
     * @param _title
     * @param _href
     * @param _width
     * @param _height
     */
    openWin: function (_title, _href, _width, _height, _domId, _param, oopts) {
        var _winDom, _method = "get";
        if (_domId) {
            _winDom = "<div id=" + _domId + "></div>";
        } else {
            _winDom = "<div></div>";
        }
        //$("body").append(_winDom);
        var _open_win = $(_winDom);
        var opt = {
            title: _title,
            href: _href,
            width: _width,
            height: _height,
            modal: true,
            minimizable: false,
            maximizable: false,
            collapsible: false,
            resizable: false,
            onClose: function () {
                _open_win.window("destroy");
            }
        }
        if (_param) {
            _method = "post";
            opt['queryParams'] = _param;
        }
        opt['method'] = _method;

        if (oopts) {
            for (var oo in oopts) {
                opt[oo] = oopts[oo];
            }
        }

        _open_win.window(opt);
    },

    closeWin: function (_domId) {
        $("#" + _domId).panel("close");
    },

    /**
     * 创建数据表格
     * @param _domId
     * @param _url
     * @param _menu
     * @param _searchForm
     * @param oopts
     */
    createDataGrid: function (_domId, _url, _menu, _searchForm, oopts, _isUnLoad) {
        var _grid = $("#" + _domId);
        var opts = {
            url: _url,
            fit: true,
            autoRowHeight: false,
            nowrap: true,
            striped: true,
            rownumbers: true,
            multiSort: true,
            flag: false,
            onHeaderContextMenu: function (e, field) {
                e.preventDefault();
                if (!cmenu) {
                    createColumnMenu();
                }
                cmenu.menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        };
        if (_menu) {
            opts.toolbar = '#' + _menu;
        }
        if (_searchForm) {
            opts.search = _searchForm;
        }
        if (_isUnLoad) {
            opts.tempInit = false;
        } else {
            opts.tempInit = true;
        }
        opts.loader = function (param, success, error) {
            if (opts.flag) {
                if (opts.tempInit) {
                    if (!opts.url) {
                        return false;
                    }
                    $.ajax({
                        type: opts.method,
                        url: opts.url,
                        data: param,
                        dataType: "json",
                        success: function (data) {
                            if (data.status == 1) {
                                if (data.rows.length == 0) {
                                    _grid.datagrid("loaded");
                                    success(data);
                                    _grid.datagrid('getPanel').find('.datagrid-view2').find('.datagrid-body').append("<div class='nodata'>未查询到数据</div>");
                                } else {
                                    success(data);
                                }
                            } else {
                                _grid.datagrid("loaded");
                                messager.alert(data.messagecode);
                            }
                        }
                    })
                } else {
                    opts.tempInit = true;
                    return false;
                }
            } else {
                opts.flag = true;
                return false;
            }
        };
        if (oopts) {
            for (var oo in oopts) {
                opts[oo] = oopts[oo];
            }
        }

        _grid.datagrid(opts);

        // -------------------------------字段筛选-----------------------------------
        var cmenu;

        function createColumnMenu() {
            cmenu = $('<div/>').appendTo('body');
            cmenu.menu({
                onClick: function (item) {
                    if (item.iconCls == 'icon-ok') {
                        _grid.datagrid('hideColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-empty'
                        });
                    } else {
                        _grid.datagrid('showColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-ok'
                        });
                    }
                    common.resizeFilter(_grid, null, 10);
                    _grid.datagrid('fitColumns');
                    common.resizeFilter(_grid);
                }
            });
            var fields = _grid.datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var col = _grid.datagrid('getColumnOption', field);
                if (col.hidden == true)
                    continue;
                cmenu.menu('appendItem', {
                    text: col.title,
                    name: field,
                    iconCls: 'icon-ok'
                });
            }
        }

        // -------------------------------字段筛选-----------------------------------
        common.loadDateGrid(_domId);
    },
    // -------------------------------调节列宽-----------------------------------

    /**
     * 创建行内编辑数据表格
     * @param _domId
     * @param _url
     * @param _menu
     * @param _searchForm
     * @param oopts
     */
    createEDataGrid: function (_domId, _url, _menu, _searchForm, oopts, _isUnLoad) {
        var _grid = $("#" + _domId);
        var opts = {
            url: _url,
            fit: true,
            autoRowHeight: false,
            nowrap: true,
            rownumbers: true,
            multiSort: true,
            flag: false,
            onHeaderContextMenu: function (e, field) {
                e.preventDefault();
                if (!cmenu) {
                    createColumnMenu();
                }
                cmenu.menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        };
        if (_menu) {
            opts.toolbar = '#' + _menu;
        }
        if (_searchForm) {
            opts.search = _searchForm;
        }
        if (_isUnLoad) {
            opts.tempInit = false;
        } else {
            opts.tempInit = true;
        }
        opts.loader = function (param, success, error) {
            if (opts.flag) {
                if (opts.tempInit) {
                    if (!opts.url) {
                        return false;
                    }
                    $.ajax({
                        type: opts.method,
                        url: opts.url,
                        data: param,
                        dataType: "json",
                        success: function (data) {
                            if (data.status == 1) {
                                if (data.rows.length == 0) {
                                    _grid.datagrid("loaded");
                                    success(data);
                                    _grid.datagrid('getPanel').find('.datagrid-view2').find('.datagrid-body').append("<div class='nodata'>未查询到数据</div>");
                                } else {
                                    success(data);
                                }
                            } else {
                                _grid.datagrid("loaded");
                                messager.alert(data.messagecode);
                            }
                        }
                    })
                } else {
                    opts.tempInit = true;
                    return false;
                }
            } else {
                opts.flag = true;
                return false;
            }
        };
        if (oopts) {
            for (var oo in oopts) {
                opts[oo] = oopts[oo];
            }
        }
        _grid.edatagrid(opts);

        // -------------------------------字段筛选-----------------------------------
        var cmenu;

        function createColumnMenu() {
            cmenu = $('<div/>').appendTo('body');
            cmenu.menu({
                onClick: function (item) {
                    if (item.iconCls == 'icon-ok') {
                        _grid.datagrid('hideColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-empty'
                        });
                    } else {
                        _grid.datagrid('showColumn', item.name);
                        cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-ok'
                        });
                    }
                    common.resizeFilter(_grid, null, 10);
                    _grid.datagrid('fitColumns');
                    common.resizeFilter(_grid);
                }
            });
            var fields = _grid.datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var col = _grid.datagrid('getColumnOption', field);
                if (col.hidden == true)
                    continue;
                cmenu.menu('appendItem', {
                    text: col.title,
                    name: field,
                    iconCls: 'icon-ok'
                });
            }
        }

        // -------------------------------字段筛选-----------------------------------
        common.loadDateGrid(_domId);
    },

    /**
     * 查询按钮查询数据
     * @param _domId
     */
    loadDateGrid: function (_domId, _param) {
        var _grid = $("#" + _domId);
        var opts = _grid.datagrid("options");
        var _searchFrom;
        if (opts && opts.search) {
            _searchFrom = $("#" + opts.search);
        }
        var param;
        if (_searchFrom) {
            param = this.serializeObject(opts.search);
        } else {
            if (_param) {
                param = _param;
            } else {
                param = {};
            }
        }
        _grid.datagrid("load", param);
    },


    /**
     * 将form表单内的元素序列化为对象，扩展Jquery的一个方法
     */
    serializeObject: function (_form) {
        var o = {};
        var form = $("#" + _form).form();
        $.each(form.serializeArray(), function (index) {
            var attr = this['name'];
            if (attr) {
                if (o[attr]) {
                    o[attr] = o[attr] + "," + this['value'];
                } else {
                    o[attr] = this['value'];
                }
            }
        });
        return o;
    }

}