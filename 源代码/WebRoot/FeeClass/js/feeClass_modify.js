$(function () {
	$.ajax({
		url : "FeeClass/" + $("#feeClass_classId_edit").val() + "/update",
		type : "get",
		data : {
			//classId : $("#feeClass_classId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (feeClass, response, status) {
			$.messager.progress("close");
			if (feeClass) { 
				$("#feeClass_classId_edit").val(feeClass.classId);
				$("#feeClass_classId_edit").validatebox({
					required : true,
					missingMessage : "请输入费用类别编号",
					editable: false
				});
				$("#feeClass_className_edit").val(feeClass.className);
				$("#feeClass_className_edit").validatebox({
					required : true,
					missingMessage : "请输入费用类别名称",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#feeClassModifyButton").click(function(){ 
		if ($("#feeClassEditForm").form("validate")) {
			$("#feeClassEditForm").form({
			    url:"FeeClass/" +  $("#feeClass_classId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#feeClassEditForm").form("validate"))  {
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#feeClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
