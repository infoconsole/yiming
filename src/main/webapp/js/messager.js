var messager={
		/**
		 * msg框
		 * @param msg		提示信息内容
		 * @param location 	位置
		 * 					屏幕位置 	123
		 * 							456
		 *  						789
		 */
		msg : function(location, msg){
			//alertMsg(msg);
			switch(location){
			case 3:
				if(undefined == msg || !msg){
					msg = "请选择一条记录信息"
				}
				$.messager.show({
					title:'提示信息',
					msg:msg,
					showType:'show',
					style:{
						left:'',
						right:0,
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});
				break;
			case 9:
				if(undefined == msg || !msg){
					msg = "操作成功"
				}
				$.messager.show({
					title:'提示信息',
					msg:msg,
					showType:'show'
				});
				break;
			default:
				$.messager.show({
					title:'提示信息',
					msg:"操作成功",
					showType:'show'
				});
			}
		},
		/**
		 * alert框
		 * @param msg
		 */
		alert : function(msg){
			$.messager.alert('提示信息', msg, 'warning');
		},
		/**
		 * confirm框
		 * @param msg
		 * @param fn
		 */
		confirm : function(msg, fn, butTxt1, butTxt2, butTxt3){
			//alertMsg(msg, "confirm", fn, butTxt1, butTxt2, butTxt3);
		}
}