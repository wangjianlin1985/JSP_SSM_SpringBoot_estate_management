var facility_manage_tool = null; 
$(function () { 
	initFacilityManageTool(); //建立Facility管理对象
	facility_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#facility_manage").datagrid({
		url : 'Facility/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "facilityId",
		sortOrder : "desc",
		toolbar : "#facility_manage_tool",
		columns : [[
			{
				field : "facilityId",
				title : "设施id",
				width : 70,
			},
			{
				field : "facilityName",
				title : "设施名称",
				width : 140,
			},
			{
				field : "facilityPhoto",
				title : "设施照片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "purpose",
				title : "设施用途",
				width : 140,
			},
			{
				field : "bornDate",
				title : "投放日期",
				width : 140,
			},
			{
				field : "fuzeren",
				title : "负责人",
				width : 140,
			},
			{
				field : "telephone",
				title : "联系电话",
				width : 140,
			},
		]],
	});

	$("#facilityEditDiv").dialog({
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
				if ($("#facilityEditForm").form("validate")) {
					//验证表单 
					if(!$("#facilityEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#facilityEditForm").form({
						    url:"Facility/" + $("#facility_facilityId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#facilityEditForm").form("validate"))  {
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
			                        $("#facilityEditDiv").dialog("close");
			                        facility_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#facilityEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#facilityEditDiv").dialog("close");
				$("#facilityEditForm").form("reset"); 
			},
		}],
	});
});

function initFacilityManageTool() {
	facility_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#facility_manage").datagrid("reload");
		},
		redo : function () {
			$("#facility_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#facility_manage").datagrid("options").queryParams;
			queryParams["facilityName"] = $("#facilityName").val();
			queryParams["bornDate"] = $("#bornDate").datebox("getValue"); 
			$("#facility_manage").datagrid("options").queryParams=queryParams; 
			$("#facility_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#facilityQueryForm").form({
			    url:"Facility/OutToExcel",
			});
			//提交表单
			$("#facilityQueryForm").submit();
		},
		remove : function () {
			var rows = $("#facility_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var facilityIds = [];
						for (var i = 0; i < rows.length; i ++) {
							facilityIds.push(rows[i].facilityId);
						}
						$.ajax({
							type : "POST",
							url : "Facility/deletes",
							data : {
								facilityIds : facilityIds.join(","),
							},
							beforeSend : function () {
								$("#facility_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#facility_manage").datagrid("loaded");
									$("#facility_manage").datagrid("load");
									$("#facility_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#facility_manage").datagrid("loaded");
									$("#facility_manage").datagrid("load");
									$("#facility_manage").datagrid("unselectAll");
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
			var rows = $("#facility_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Facility/" + rows[0].facilityId +  "/update",
					type : "get",
					data : {
						//facilityId : rows[0].facilityId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (facility, response, status) {
						$.messager.progress("close");
						if (facility) { 
							$("#facilityEditDiv").dialog("open");
							$("#facility_facilityId_edit").val(facility.facilityId);
							$("#facility_facilityId_edit").validatebox({
								required : true,
								missingMessage : "请输入设施id",
								editable: false
							});
							$("#facility_facilityName_edit").val(facility.facilityName);
							$("#facility_facilityName_edit").validatebox({
								required : true,
								missingMessage : "请输入设施名称",
							});
							$("#facility_facilityPhoto").val(facility.facilityPhoto);
							$("#facility_facilityPhotoImg").attr("src", facility.facilityPhoto);
							$("#facility_purpose_edit").val(facility.purpose);
							$("#facility_purpose_edit").validatebox({
								required : true,
								missingMessage : "请输入设施用途",
							});
							$("#facility_bornDate_edit").datebox({
								value: facility.bornDate,
							    required: true,
							    showSeconds: true,
							});
							$("#facility_fuzeren_edit").val(facility.fuzeren);
							$("#facility_fuzeren_edit").validatebox({
								required : true,
								missingMessage : "请输入负责人",
							});
							$("#facility_telephone_edit").val(facility.telephone);
							$("#facility_telephone_edit").validatebox({
								required : true,
								missingMessage : "请输入联系电话",
							});
							$("#facility_facilityMemo_edit").val(facility.facilityMemo);
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
