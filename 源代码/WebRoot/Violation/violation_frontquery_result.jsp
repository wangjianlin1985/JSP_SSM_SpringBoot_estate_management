<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Violation" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Violation> violationList = (List<Violation>)request.getAttribute("violationList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String title = (String)request.getAttribute("title"); //违规简述查询关键字
    String handleState = (String)request.getAttribute("handleState"); //处理状态查询关键字
    String uploadTime = (String)request.getAttribute("uploadTime"); //举报时间查询关键字
    UserInfo reportUserObj = (UserInfo)request.getAttribute("reportUserObj");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>违规现象查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#violationListPanel" aria-controls="violationListPanel" role="tab" data-toggle="tab">违规现象列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Violation/violation_frontAdd.jsp" style="display:none;">添加违规现象</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="violationListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>违规id</td><td>违规住户</td><td>违规简述</td><td>处理状态</td><td>举报时间</td><td>举报人</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<violationList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Violation violation = violationList.get(i); //获取到违规现象对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=violation.getViolationId() %></td>
 											<td><%=violation.getUserObj().getRoomNo() %></td>
 											<td><%=violation.getTitle() %></td>
 											<td><%=violation.getHandleState() %></td>
 											<td><%=violation.getUploadTime() %></td>
 											<td><%=violation.getReportUserObj().getRoomNo() %></td>
 											<td>
 												<a href="<%=basePath  %>Violation/<%=violation.getViolationId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="violationEdit('<%=violation.getViolationId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="violationDelete('<%=violation.getViolationId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>违规现象查询</h1>
		</div>
		<form name="violationQueryForm" id="violationQueryForm" action="<%=basePath %>Violation/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="userObj_user_name">违规住户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getRoomNo() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="title">违规简述:</label>
				<input type="text" id="title" name="title" value="<%=title %>" class="form-control" placeholder="请输入违规简述">
			</div>






			<div class="form-group">
				<label for="handleState">处理状态:</label>
				<input type="text" id="handleState" name="handleState" value="<%=handleState %>" class="form-control" placeholder="请输入处理状态">
			</div>






			<div class="form-group">
				<label for="uploadTime">举报时间:</label>
				<input type="text" id="uploadTime" name="uploadTime" class="form-control"  placeholder="请选择举报时间" value="<%=uploadTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <div class="form-group">
            	<label for="reportUserObj_user_name">举报人：</label>
                <select id="reportUserObj_user_name" name="reportUserObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(reportUserObj!=null && reportUserObj.getUser_name()!=null && reportUserObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getRoomNo() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="violationEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;违规现象信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="violationEditForm" id="violationEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="violation_violationId_edit" class="col-md-3 text-right">违规id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="violation_violationId_edit" name="violation.violationId" class="form-control" placeholder="请输入违规id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="violation_userObj_user_name_edit" class="col-md-3 text-right">违规住户:</label>
		  	 <div class="col-md-9">
			    <select id="violation_userObj_user_name_edit" name="violation.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_title_edit" class="col-md-3 text-right">违规简述:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="violation_title_edit" name="violation.title" class="form-control" placeholder="请输入违规简述">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_content_edit" class="col-md-3 text-right">违规详情:</label>
		  	 <div class="col-md-9">
			 	<textarea name="violation.content" id="violation_content_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_handleState_edit" class="col-md-3 text-right">处理状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="violation_handleState_edit" name="violation.handleState" class="form-control" placeholder="请输入处理状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_handleResult_edit" class="col-md-3 text-right">处理结果:</label>
		  	 <div class="col-md-9">
			 	<textarea name="violation.handleResult" id="violation_handleResult_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_uploadTime_edit" class="col-md-3 text-right">举报时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date violation_uploadTime_edit col-md-12" data-link-field="violation_uploadTime_edit">
                    <input class="form-control" id="violation_uploadTime_edit" name="violation.uploadTime" size="16" type="text" value="" placeholder="请选择举报时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="violation_reportUserObj_user_name_edit" class="col-md-3 text-right">举报人:</label>
		  	 <div class="col-md-9">
			    <select id="violation_reportUserObj_user_name_edit" name="violation.reportUserObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		</form> 
	    <style>#violationEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxViolationModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var violation_content_edit = UE.getEditor('violation_content_edit'); //违规详情编辑器
var violation_handleResult_edit = UE.getEditor('violation_handleResult_edit'); //处理结果编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.violationQueryForm.currentPage.value = currentPage;
    document.violationQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.violationQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.violationQueryForm.currentPage.value = pageValue;
    documentviolationQueryForm.submit();
}

/*弹出修改违规现象界面并初始化数据*/
function violationEdit(violationId) {
	$.ajax({
		url :  basePath + "Violation/" + violationId + "/update",
		type : "get",
		dataType: "json",
		success : function (violation, response, status) {
			if (violation) {
				$("#violation_violationId_edit").val(violation.violationId);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#violation_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.roomNo + "</option>";
		        		});
		        		$("#violation_userObj_user_name_edit").html(html);
		        		$("#violation_userObj_user_name_edit").val(violation.userObjPri);
					}
				});
				$("#violation_title_edit").val(violation.title);
				violation_content_edit.setContent(violation.content, false);
				$("#violation_handleState_edit").val(violation.handleState);
				violation_handleResult_edit.setContent(violation.handleResult, false);
				$("#violation_uploadTime_edit").val(violation.uploadTime);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#violation_reportUserObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.roomNo + "</option>";
		        		});
		        		$("#violation_reportUserObj_user_name_edit").html(html);
		        		$("#violation_reportUserObj_user_name_edit").val(violation.reportUserObjPri);
					}
				});
				$('#violationEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除违规现象信息*/
function violationDelete(violationId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Violation/deletes",
			data : {
				violationIds : violationId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#violationQueryForm").submit();
					//location.href= basePath + "Violation/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交违规现象信息表单给服务器端修改*/
function ajaxViolationModify() {
	$.ajax({
		url :  basePath + "Violation/" + $("#violation_violationId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#violationEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#violationQueryForm").submit();
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

    /*举报时间组件*/
    $('.violation_uploadTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

