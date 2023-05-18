$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('violation_content');
	var violation_content_editor = UE.getEditor('violation_content'); //违规详情编辑框
	UE.delEditor('violation_handleResult');
	var violation_handleResult_editor = UE.getEditor('violation_handleResult'); //处理结果编辑框
	$("#violation_userObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "roomNo",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#violation_userObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#violation_userObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#violation_title").validatebox({
		required : true, 
		missingMessage : '请输入违规简述',
	});

	$("#violation_handleState").validatebox({
		required : true, 
		missingMessage : '请输入处理状态',
	});

	$("#violation_uploadTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#violation_reportUserObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "roomNo",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#violation_reportUserObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#violation_reportUserObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	//单击添加按钮
	$("#violationAddButton").click(function () {
		if(violation_content_editor.getContent() == "") {
			alert("请输入违规详情");
			return;
		}
		if(violation_handleResult_editor.getContent() == "") {
			alert("请输入处理结果");
			return;
		}
		//验证表单 
		if(!$("#violationAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#violationAddForm").form({
			    url:"Violation/add",
			    onSubmit: function(){
					if($("#violationAddForm").form("validate"))  { 
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
                        $("#violationAddForm").form("clear");
                        violation_content_editor.setContent("");
                        violation_handleResult_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#violationAddForm").submit();
		}
	});

	//单击清空按钮
	$("#violationClearButton").click(function () { 
		$("#violationAddForm").form("clear"); 
	});
});
