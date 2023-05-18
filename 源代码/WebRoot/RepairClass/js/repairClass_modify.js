$(function () {
	$.ajax({
		url : "RepairClass/" + $("#repairClass_repairClassId_edit").val() + "/update",
		type : "get",
		data : {
			//repairClassId : $("#repairClass_repairClassId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (repairClass, response, status) {
			$.messager.progress("close");
			if (repairClass) { 
				$("#repairClass_repairClassId_edit").val(repairClass.repairClassId);
				$("#repairClass_repairClassId_edit").validatebox({
					required : true,
					missingMessage : "请输入报修分类id",
					editable: false
				});
				$("#repairClass_repairClassName_edit").val(repairClass.repairClassName);
				$("#repairClass_repairClassName_edit").validatebox({
					required : true,
					missingMessage : "请输入报修分类名称",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#repairClassModifyButton").click(function(){ 
		if ($("#repairClassEditForm").form("validate")) {
			$("#repairClassEditForm").form({
			    url:"RepairClass/" +  $("#repairClass_repairClassId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#repairClassEditForm").form("validate"))  {
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
			$("#repairClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
