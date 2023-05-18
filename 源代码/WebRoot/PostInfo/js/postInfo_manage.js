var postInfo_manage_tool = null; 
$(function () { 
	initPostInfoManageTool(); //建立PostInfo管理对象
	postInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#postInfo_manage").datagrid({
		url : 'PostInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "postInfoId",
		sortOrder : "desc",
		toolbar : "#postInfo_manage_tool",
		columns : [[
			{
				field : "postInfoId",
				title : "帖子id",
				width : 70,
			},
			{
				field : "title",
				title : "帖子标题",
				width : 140,
			},
			{
				field : "hitNum",
				title : "浏览量",
				width : 70,
			},
			{
				field : "userObj",
				title : "发帖人",
				width : 140,
			},
			{
				field : "addTime",
				title : "发帖时间",
				width : 140,
			},
		]],
	});

	$("#postInfoEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#postInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#postInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#postInfoEditForm").form({
						    url:"PostInfo/" + $("#postInfo_postInfoId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#postInfoEditDiv").dialog("close");
			                        postInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#postInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#postInfoEditDiv").dialog("close");
				$("#postInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initPostInfoManageTool() {
	postInfo_manage_tool = {
		init: function() {
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#userObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"roomNo",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",roomNo:"不限制"});
					$("#userObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#postInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#postInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#postInfo_manage").datagrid("options").queryParams;
			queryParams["title"] = $("#title").val();
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#postInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#postInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#postInfoQueryForm").form({
			    url:"PostInfo/OutToExcel",
			});
			//提交表单
			$("#postInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#postInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var postInfoIds = [];
						for (var i = 0; i < rows.length; i ++) {
							postInfoIds.push(rows[i].postInfoId);
						}
						$.ajax({
							type : "POST",
							url : "PostInfo/deletes",
							data : {
								postInfoIds : postInfoIds.join(","),
							},
							beforeSend : function () {
								$("#postInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#postInfo_manage").datagrid("loaded");
									$("#postInfo_manage").datagrid("load");
									$("#postInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#postInfo_manage").datagrid("loaded");
									$("#postInfo_manage").datagrid("load");
									$("#postInfo_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#postInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "PostInfo/" + rows[0].postInfoId +  "/update",
					type : "get",
					data : {
						//postInfoId : rows[0].postInfoId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (postInfo, response, status) {
						$.messager.progress("close");
						if (postInfo) { 
							$("#postInfoEditDiv").dialog("open");
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
							postInfo_content_editor.setContent(postInfo.content, false);
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
