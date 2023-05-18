<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.FeeClass" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    FeeClass feeClass = (FeeClass)request.getAttribute("feeClass");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改费用类别信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">费用类别信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="feeClassEditForm" id="feeClassEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="feeClass_classId_edit" class="col-md-3 text-right">费用类别编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="feeClass_classId_edit" name="feeClass.classId" class="form-control" placeholder="请输入费用类别编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="feeClass_className_edit" class="col-md-3 text-right">费用类别名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="feeClass_className_edit" name="feeClass.className" class="form-control" placeholder="请输入费用类别名称">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxFeeClassModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#feeClassEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改费用类别界面并初始化数据*/
function feeClassEdit(classId) {
	$.ajax({
		url :  basePath + "FeeClass/" + classId + "/update",
		type : "get",
		dataType: "json",
		success : function (feeClass, response, status) {
			if (feeClass) {
				$("#feeClass_classId_edit").val(feeClass.classId);
				$("#feeClass_className_edit").val(feeClass.className);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交费用类别信息表单给服务器端修改*/
function ajaxFeeClassModify() {
	$.ajax({
		url :  basePath + "FeeClass/" + $("#feeClass_classId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#feeClassEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                location.href= basePath + "FeeClass/frontlist";
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    feeClassEdit("<%=request.getParameter("classId")%>");
 })
 </script> 
</body>
</html>

