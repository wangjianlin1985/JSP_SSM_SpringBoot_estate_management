$(function () {
	$("#repairClass_repairClassName").validatebox({
		required : true, 
		missingMessage : '请输入报修分类名称',
	});

	//单击添加按钮
	$("#repairClassAddButton").click(function () {
		//验证表单 
		if(!$("#repairClassAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#repairClassAddForm").form({
			    url:"RepairClass/add",
			    onSubmit: function(){
					if($("#repairClassAddForm").form("validate"))  { 
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
                        $("#repairClassAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#repairClassAddForm").submit();
		}
	});

	//单击清空按钮
	$("#repairClassClearButton").click(function () { 
		$("#repairClassAddForm").form("clear"); 
	});
});
