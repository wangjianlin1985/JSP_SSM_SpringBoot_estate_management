<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/repairClass.css" />
<div id="repairClass_editDiv">
	<form id="repairClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">报修分类id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="repairClass_repairClassId_edit" name="repairClass.repairClassId" value="<%=request.getParameter("repairClassId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">报修分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="repairClass_repairClassName_edit" name="repairClass.repairClassName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="repairClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/RepairClass/js/repairClass_modify.js"></script> 
