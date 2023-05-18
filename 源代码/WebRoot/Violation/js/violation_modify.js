$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('violation_content_edit');
	var violation_content_edit = UE.getEditor('violation_content_edit'); //违规详情编辑器
	violation_content_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
	UE.delEditor('violation_handleResult_edit');
	var violation_handleResult_edit = UE.getEditor('violation_handleResult_edit'); //处理结果编辑器
	violation_handleResult_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Violation/" + $("#violation_violationId_edit").val() + "/update",
		type : "get",
		data : {
			//violationId : $("#violation_violationId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (violation, response, status) {
			$.messager.progress("close");
			if (violation) { 
				$("#violation_violationId_edit").val(violation.violationId);
				$("#violation_violationId_edit").validatebox({
					required : true,
					missingMessage : "请输入违规id",
					editable: false
				});
				$("#violation_userObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"roomNo",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#violation_userObj_user_name_edit").combobox("select", violation.userObjPri);
						//var data = $("#violation_userObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#violation_userObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
				$("#violation_title_edit").val(violation.title);
				$("#violation_title_edit").validatebox({
					required : true,
					missingMessage : "请输入违规简述",
				});
				violation_content_edit.setContent(violation.content);
				$("#violation_handleState_edit").val(violation.handleState);
				$("#violation_handleState_edit").validatebox({
					required : true,
					missingMessage : "请输入处理状态",
				});
				violation_handleResult_edit.setContent(violation.handleResult);
				$("#violation_uploadTime_edit").datetimebox({
					value: violation.uploadTime,
					required: true,
					showSeconds: true,
				});
				$("#violation_reportUserObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"roomNo",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#violation_reportUserObj_user_name_edit").combobox("select", violation.reportUserObjPri);
						//var data = $("#violation_reportUserObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#violation_reportUserObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#violationModifyButton").click(function(){ 
		if ($("#violationEditForm").form("validate")) {
			$("#violationEditForm").form({
			    url:"Violation/" +  $("#violation_violationId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#violationEditForm").form("validate"))  {
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
			$("#violationEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
