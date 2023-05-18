<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/feeClass.css" />
<div id="feeClassAddDiv">
	<form id="feeClassAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">费用类别名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="feeClass_className" name="feeClass.className" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="feeClassAddButton" class="easyui-linkbutton">添加</a>
			<a id="feeClassClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/FeeClass/js/feeClass_add.js"></script> 
