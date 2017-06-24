var ajaxUtil = {
		get: function(_url, _param, isAsync, callback){
			ajax(_url, "get", _param, isAsync, "application/x-www-form-urlencoded; charset=utf-8", callback)
		},
		
		post: function(_url, _param, isAsync, callback){
			ajax(_url, "post", _param, isAsync, "application/x-www-form-urlencoded; charset=utf-8", callback);
		},
		getJsonList: function(_url, _param, isAsync, callback){
			ajax(_url, "get", _param, isAsync, "application/json; charset=utf-8", callback)
		},
		
		postJsonList: function(_url, _param, isAsync, callback){
			ajax(_url, "post", _param, isAsync, "application/json; charset=utf-8", callback);
		}
}

function ajax(_url, _method, _param, isAsync, isJsonList, callback){
	if(isAsync){
		isAsync = true;
	}else{
		isAsync = false;
	}
	$.ajax({
		method : _method,
		url : _url,
		data : _param,
		async : isAsync, 
		traditional:true,
		dataType : 'json',
		contentType : isJsonList,
		success : function(data) {
			if(data.status != undefined && data.status == 0){
				messager.msg(3,data.messagecode);
				// throw new Error ("error");
			}else{
				if(callback){
					callback(data);
				}
			}
		},
		error : function(){
			//messager.alert("服务器异常！");
			throw new Error ("error");
		}
	})
}