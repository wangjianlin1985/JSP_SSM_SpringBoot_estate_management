$(function () {
	$.ajax({
		url : "Facility/" + $("#facility_facilityId_edit").val() + "/update",
		type : "get",
		data : {
			//facilityId : $("#facility_facilityId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (facility, response, status) {
			$.messager.progress("close");
			if (facility) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#facilityModifyButton").click(function(){ 
		if ($("#facilityEditForm").form("validate")) {
			$("#facilityEditForm").form({
			    url:"Facility/" +  $("#facility_facilityId_edit").val() + "/update",
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
			$("#facilityEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
