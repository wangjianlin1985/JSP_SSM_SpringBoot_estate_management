var feeClass_manage_tool = null; 
$(function () { 
	initFeeClassManageTool(); //建立FeeClass管理对象
	feeClass_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#feeClass_manage").datagrid({
		url : 'FeeClass/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "classId",
		sortOrder : "desc",
		toolbar : "#feeClass_manage_tool",
		columns : [[
			{
				field : "classId",
				title : "费用类别编号",
				width : 70,
			},
			{
				field : "className",
				title : "费用类别名称",
				width : 140,
			},
		]],
	});

	$("#feeClassEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#feeClassEditForm").form("validate")) {
					//验证表单 
					if(!$("#feeClassEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#feeClassEditForm").form({
						    url:"FeeClass/" + $("#feeClass_classId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#feeClassEditDiv").dialog("close");
			                        feeClass_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#feeClassEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#feeClassEditDiv").dialog("close");
				$("#feeClassEditForm").form("reset"); 
			},
		}],
	});
});

function initFeeClassManageTool() {
	feeClass_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#feeClass_manage").datagrid("reload");
		},
		redo : function () {
			$("#feeClass_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#feeClass_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#feeClassQueryForm").form({
			    url:"FeeClass/OutToExcel",
			});
			//提交表单
			$("#feeClassQueryForm").submit();
		},
		remove : function () {
			var rows = $("#feeClass_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var classIds = [];
						for (var i = 0; i < rows.length; i ++) {
							classIds.push(rows[i].classId);
						}
						$.ajax({
							type : "POST",
							url : "FeeClass/deletes",
							data : {
								classIds : classIds.join(","),
							},
							beforeSend : function () {
								$("#feeClass_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#feeClass_manage").datagrid("loaded");
									$("#feeClass_manage").datagrid("load");
									$("#feeClass_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#feeClass_manage").datagrid("loaded");
									$("#feeClass_manage").datagrid("load");
									$("#feeClass_manage").datagrid("unselectAll");
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
			var rows = $("#feeClass_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "FeeClass/" + rows[0].classId +  "/update",
					type : "get",
					data : {
						//classId : rows[0].classId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (feeClass, response, status) {
						$.messager.progress("close");
						if (feeClass) { 
							$("#feeClassEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
