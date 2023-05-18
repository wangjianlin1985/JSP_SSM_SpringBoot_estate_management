var violation_manage_tool = null; 
$(function () { 
	initViolationManageTool(); //建立Violation管理对象
	violation_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#violation_manage").datagrid({
		url : 'Violation/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "violationId",
		sortOrder : "desc",
		toolbar : "#violation_manage_tool",
		columns : [[
			{
				field : "violationId",
				title : "违规id",
				width : 70,
			},
			{
				field : "userObj",
				title : "违规住户",
				width : 140,
			},
			{
				field : "title",
				title : "违规简述",
				width : 140,
			},
			{
				field : "handleState",
				title : "处理状态",
				width : 140,
			},
			{
				field : "uploadTime",
				title : "举报时间",
				width : 140,
			},
			{
				field : "reportUserObj",
				title : "举报人",
				width : 140,
			},
		]],
	});

	$("#violationEditDiv").dialog({
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
				if ($("#violationEditForm").form("validate")) {
					//验证表单 
					if(!$("#violationEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#violationEditForm").form({
						    url:"Violation/" + $("#violation_violationId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#violationEditDiv").dialog("close");
			                        violation_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#violationEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#violationEditDiv").dialog("close");
				$("#violationEditForm").form("reset"); 
			},
		}],
	});
});

function initViolationManageTool() {
	violation_manage_tool = {
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
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#reportUserObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"roomNo",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",roomNo:"不限制"});
					$("#reportUserObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#violation_manage").datagrid("reload");
		},
		redo : function () {
			$("#violation_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#violation_manage").datagrid("options").queryParams;
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["title"] = $("#title").val();
			queryParams["handleState"] = $("#handleState").val();
			queryParams["uploadTime"] = $("#uploadTime").datebox("getValue"); 
			queryParams["reportUserObj.user_name"] = $("#reportUserObj_user_name_query").combobox("getValue");
			$("#violation_manage").datagrid("options").queryParams=queryParams; 
			$("#violation_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#violationQueryForm").form({
			    url:"Violation/OutToExcel",
			});
			//提交表单
			$("#violationQueryForm").submit();
		},
		remove : function () {
			var rows = $("#violation_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var violationIds = [];
						for (var i = 0; i < rows.length; i ++) {
							violationIds.push(rows[i].violationId);
						}
						$.ajax({
							type : "POST",
							url : "Violation/deletes",
							data : {
								violationIds : violationIds.join(","),
							},
							beforeSend : function () {
								$("#violation_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#violation_manage").datagrid("loaded");
									$("#violation_manage").datagrid("load");
									$("#violation_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#violation_manage").datagrid("loaded");
									$("#violation_manage").datagrid("load");
									$("#violation_manage").datagrid("unselectAll");
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
			var rows = $("#violation_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Violation/" + rows[0].violationId +  "/update",
					type : "get",
					data : {
						//violationId : rows[0].violationId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (violation, response, status) {
						$.messager.progress("close");
						if (violation) { 
							$("#violationEditDiv").dialog("open");
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
							violation_content_editor.setContent(violation.content, false);
							$("#violation_handleState_edit").val(violation.handleState);
							$("#violation_handleState_edit").validatebox({
								required : true,
								missingMessage : "请输入处理状态",
							});
							violation_handleResult_editor.setContent(violation.handleResult, false);
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
