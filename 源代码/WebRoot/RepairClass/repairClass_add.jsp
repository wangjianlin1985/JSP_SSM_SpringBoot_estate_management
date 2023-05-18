<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/repairClass.css" />
<div id="repairClassAddDiv">
	<form id="repairClassAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">报修分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="repairClass_repairClassName" name="repairClass.repairClassName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="repairClassAddButton" class="easyui-linkbutton">添加</a>
			<a id="repairClassClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/RepairClass/js/repairClass_add.js"></script> 
