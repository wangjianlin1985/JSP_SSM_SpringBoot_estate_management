var repairClass_manage_tool = null; 
$(function () { 
	initRepairClassManageTool(); //建立RepairClass管理对象
	repairClass_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#repairClass_manage").datagrid({
		url : 'RepairClass/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "repairClassId",
		sortOrder : "desc",
		toolbar : "#repairClass_manage_tool",
		columns : [[
			{
				field : "repairClassId",
				title : "报修分类id",
				width : 70,
			},
			{
				field : "repairClassName",
				title : "报修分类名称",
				width : 140,
			},
		]],
	});

	$("#repairClassEditDiv").dialog({
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
				if ($("#repairClassEditForm").form("validate")) {
					//验证表单 
					if(!$("#repairClassEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#repairClassEditForm").form({
						    url:"RepairClass/" + $("#repairClass_repairClassId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#repairClassEditDiv").dialog("close");
			                        repairClass_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#repairClassEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#repairClassEditDiv").dialog("close");
				$("#repairClassEditForm").form("reset"); 
			},
		}],
	});
});

function initRepairClassManageTool() {
	repairClass_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#repairClass_manage").datagrid("reload");
		},
		redo : function () {
			$("#repairClass_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#repairClass_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#repairClassQueryForm").form({
			    url:"RepairClass/OutToExcel",
			});
			//提交表单
			$("#repairClassQueryForm").submit();
		},
		remove : function () {
			var rows = $("#repairClass_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var repairClassIds = [];
						for (var i = 0; i < rows.length; i ++) {
							repairClassIds.push(rows[i].repairClassId);
						}
						$.ajax({
							type : "POST",
							url : "RepairClass/deletes",
							data : {
								repairClassIds : repairClassIds.join(","),
							},
							beforeSend : function () {
								$("#repairClass_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#repairClass_manage").datagrid("loaded");
									$("#repairClass_manage").datagrid("load");
									$("#repairClass_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#repairClass_manage").datagrid("loaded");
									$("#repairClass_manage").datagrid("load");
									$("#repairClass_manage").datagrid("unselectAll");
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
			var rows = $("#repairClass_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "RepairClass/" + rows[0].repairClassId +  "/update",
					type : "get",
					data : {
						//repairClassId : rows[0].repairClassId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (repairClass, response, status) {
						$.messager.progress("close");
						if (repairClass) { 
							$("#repairClassEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
