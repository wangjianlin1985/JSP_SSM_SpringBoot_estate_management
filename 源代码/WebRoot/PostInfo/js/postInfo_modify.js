$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('postInfo_content_edit');
	var postInfo_content_edit = UE.getEditor('postInfo_content_edit'); //帖子内容编辑器
	postInfo_content_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "PostInfo/" + $("#postInfo_postInfoId_edit").val() + "/update",
		type : "get",
		data : {
			//postInfoId : $("#postInfo_postInfoId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (postInfo, response, status) {
			$.messager.progress("close");
			if (postInfo) { 
				$("#postInfo_postInfoId_edit").val(postInfo.postInfoId);
				$("#postInfo_postInfoId_edit").validatebox({
					required : true,
					missingMessage : "请输入帖子id",
					editable: false
				});
				$("#postInfo_title_edit").val(postInfo.title);
				$("#postInfo_title_edit").validatebox({
					required : true,
					missingMessage : "请输入帖子标题",
				});
				postInfo_content_edit.setContent(postInfo.content);
				$("#postInfo_hitNum_edit").val(postInfo.hitNum);
				$("#postInfo_hitNum_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入浏览量",
					invalidMessage : "浏览量输入不对",
				});
				$("#postInfo_userObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"roomNo",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#postInfo_userObj_user_name_edit").combobox("select", postInfo.userObjPri);
						//var data = $("#postInfo_userObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#postInfo_userObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
				$("#postInfo_addTime_edit").datetimebox({
					value: postInfo.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#postInfoModifyButton").click(function(){ 
		if ($("#postInfoEditForm").form("validate")) {
			$("#postInfoEditForm").form({
			    url:"PostInfo/" +  $("#postInfo_postInfoId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#postInfoEditForm").form("validate"))  {
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
			$("#postInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
