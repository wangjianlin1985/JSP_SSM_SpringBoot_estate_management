$(function () {
	$("#userInfo_user_name").validatebox({
		required : true, 
		missingMessage : '请输入用户名',
	});

	$("#userInfo_password").validatebox({
		required : true, 
		missingMessage : '请输入登录密码',
	});

	$("#userInfo_buildingObj_buildingId").combobox({
	    url:'Building/listAll',
	    valueField: "buildingId",
	    textField: "buildingName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#userInfo_buildingObj_buildingId").combobox("getData"); 
            if (data.length > 0) {
                $("#userInfo_buildingObj_buildingId").combobox("select", data[0].buildingId);
            }
        }
	});
	$("#userInfo_roomNo").validatebox({
		required : true, 
		missingMessage : '请输入房间号',
	});

	$("#userInfo_name").validatebox({
		required : true, 
		missingMessage : '请输入姓名',
	});

	$("#userInfo_gender").validatebox({
		required : true, 
		missingMessage : '请输入性别',
	});

	$("#userInfo_birthDate").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#userInfo_telephone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	$("#userInfo_regTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#userInfoAddButton").click(function () {
		//验证表单 
		if(!$("#userInfoAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#userInfoAddForm").form({
			    url:"UserInfo/add",
			    onSubmit: function(){
					if($("#userInfoAddForm").form("validate"))  { 
	                	$.messager.progress({
							text : "正在提交数据中...",
						}); 
	                	return true;
	                } else {
	                    return false;
	                }
			    },
			    success:function(data){
			    	$.messager.progress("close");
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#userInfoAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#userInfoAddForm").submit();
		}
	});

	//单击清空按钮
	$("#userInfoClearButton").click(function () { 
		$("#userInfoAddForm").form("clear"); 
	});
});
