<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/facility.css" /> 

<div id="facility_manage"></div>
<div id="facility_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="facility_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="facility_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="facility_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="facility_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="facility_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="facilityQueryForm" method="post">
			设施名称：<input type="text" class="textbox" id="facilityName" name="facilityName" style="width:110px" />
			投放日期：<input type="text" id="bornDate" name="bornDate" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="facility_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="facilityEditDiv">
	<form id="facilityEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">设施id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="facility_facilityId_edit" name="facility.facilityId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">设施名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="facility_facilityName_edit" name="facility.facilityName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">设施照片:</span>
			<span class="inputControl">
				<img id="facility_facilityPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="facility_facilityPhoto" name="facility.facilityPhoto"/>
				<input id="facilityPhotoFile" name="facilityPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">设施用途:</span>
			<span class="inputControl">
				<textarea id="facility_purpose_edit" name="facility.purpose" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">投放日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="facility_bornDate_edit" name="facility.bornDate" />

			</span>

		</div>
		<div>
			<span class="label">负责人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="facility_fuzeren_edit" name="facility.fuzeren" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="facility_telephone_edit" name="facility.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">设施备注:</span>
			<span class="inputControl">
				<textarea id="facility_facilityMemo_edit" name="facility.facilityMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Facility/js/facility_manage.js"></script> 
