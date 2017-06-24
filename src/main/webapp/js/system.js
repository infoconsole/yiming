var system = {
    /**
     * 创建下拉框
     * @param _domId
     * @param _data
     * @param oopts
     */
    createComboItem: function (_domId, _data, oopts) {
        var _combo = $("#" + _domId);
        var opts = {
            valueField: 'id',
            textField: 'text',
            data: _data
        }
        if (oopts) {
            for (var oo in oopts) {
                opts[oo] = oopts[oo];
            }
        }
        _combo.combobox(opts);
        return _combo;
    },

    resetComboItems: function (_domId, _isCombo) {
        var combo = $("#" + _domId);
        var data = [];
        if (_isCombo) {
            jsondata = [{"text": "全部", "id": ""}];
            data = jsondata.concat(data);
        }
        if (combo) {
            var options = {
                value: "",
                valueField: 'id',
                textField: 'text',
                data: data
            };
            combo.combobox(options);
        }
    },


    loadComboItems: function (_domId, _url, _param, oopts, _isComb, target, value, offset) {
        var data;
        if (target && value && target[value] != null && target[value] != undefined && target[value].length > 0) {
            data = target[value];
        } else {
            ajaxUtil.post(_url, _param, false, function (_data) {
                if (_data.status != 0) {
                    data = _data.rows;
                    if (target && value) {
                        target[value] = data;
                    }
                } else {
                    return;
                }
            });
        }
        if (_isComb) {
            jsondata = [{"text": "全部", "id": "", "selected": true}];
            data = jsondata.concat(data);
        }
        var combo = $("#" + _domId);
        if (combo && data) {
            var options = {
                valueField: 'id',
                textField: 'text',
                data: data
            };
            if (offset) {
                options['offset'] = offset;
            }
            if (oopts) {
                for (var o in oopts) {
                    options[o] = oopts[o];
                }
            }
            combo.combobox(options);
        }
    }
}