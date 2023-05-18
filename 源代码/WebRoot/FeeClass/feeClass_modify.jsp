<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/feeClass.css" />
<div id="feeClass_editDiv">
	<form id="feeClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">费用类别编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="feeClass_classId_edit" name="feeClass.classId" value="<%=request.getParameter("classId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">费用类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="feeClass_className_edit" name="feeClass.className" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="feeClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/FeeClass/js/feeClass_modify.js"></script> 
