<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Facility" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Facility> facilityList = (List<Facility>)request.getAttribute("facilityList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String facilityName = (String)request.getAttribute("facilityName"); //设施名称查询关键字
    String bornDate = (String)request.getAttribute("bornDate"); //投放日期查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>基础设施查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Facility/frontlist">基础设施信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Facility/facility_frontAdd.jsp" style="display:none;">添加基础设施</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<facilityList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Facility facility = facilityList.get(i); //获取到基础设施对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Facility/<%=facility.getFacilityId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=facility.getFacilityPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		设施id:<%=facility.getFacilityId() %>
			     	</div>
			     	<div class="field">
	            		设施名称:<%=facility.getFacilityName() %>
			     	</div>
			     	<div class="field">
	            		设施用途:<%=facility.getPurpose() %>
			     	</div>
			     	<div class="field">
	            		投放日期:<%=facility.getBornDate() %>
			     	</div>
			     	<div class="field">
	            		负责人:<%=facility.getFuzeren() %>
			     	</div>
			     	<div class="field">
	            		联系电话:<%=facility.getTelephone() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Facility/<%=facility.getFacilityId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="facilityEdit('<%=facility.getFacilityId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="facilityDelete('<%=facility.getFacilityId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

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

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>基础设施查询</h1>
		</div>
		<form name="facilityQueryForm" id="facilityQueryForm" action="<%=basePath %>Facility/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="facilityName">设施名称:</label>
				<input type="text" id="facilityName" name="facilityName" value="<%=facilityName %>" class="form-control" placeholder="请输入设施名称">
			</div>
			<div class="form-group">
				<label for="bornDate">投放日期:</label>
				<input type="text" id="bornDate" name="bornDate" class="form-control"  placeholder="请选择投放日期" value="<%=bornDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="facilityEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;基础设施信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="facilityEditForm" id="facilityEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="facility_facilityId_edit" class="col-md-3 text-right">设施id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="facility_facilityId_edit" name="facility.facilityId" class="form-control" placeholder="请输入设施id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="facility_facilityName_edit" class="col-md-3 text-right">设施名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="facility_facilityName_edit" name="facility.facilityName" class="form-control" placeholder="请输入设施名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_facilityPhoto_edit" class="col-md-3 text-right">设施照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="facility_facilityPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="facility_facilityPhoto" name="facility.facilityPhoto"/>
			    <input id="facilityPhotoFile" name="facilityPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_purpose_edit" class="col-md-3 text-right">设施用途:</label>
		  	 <div class="col-md-9">
			    <textarea id="facility_purpose_edit" name="facility.purpose" rows="8" class="form-control" placeholder="请输入设施用途"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_bornDate_edit" class="col-md-3 text-right">投放日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date facility_bornDate_edit col-md-12" data-link-field="facility_bornDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="facility_bornDate_edit" name="facility.bornDate" size="16" type="text" value="" placeholder="请选择投放日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_fuzeren_edit" class="col-md-3 text-right">负责人:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="facility_fuzeren_edit" name="facility.fuzeren" class="form-control" placeholder="请输入负责人">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="facility_telephone_edit" name="facility.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="facility_facilityMemo_edit" class="col-md-3 text-right">设施备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="facility_facilityMemo_edit" name="facility.facilityMemo" rows="8" class="form-control" placeholder="请输入设施备注"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#facilityEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxFacilityModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.facilityQueryForm.currentPage.value = currentPage;
    document.facilityQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.facilityQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.facilityQueryForm.currentPage.value = pageValue;
    documentfacilityQueryForm.submit();
}

/*弹出修改基础设施界面并初始化数据*/
function facilityEdit(facilityId) {
	$.ajax({
		url :  basePath + "Facility/" + facilityId + "/update",
		type : "get",
		dataType: "json",
		success : function (facility, response, status) {
			if (facility) {
				$("#facility_facilityId_edit").val(facility.facilityId);
				$("#facility_facilityName_edit").val(facility.facilityName);
				$("#facility_facilityPhoto").val(facility.facilityPhoto);
				$("#facility_facilityPhotoImg").attr("src", basePath +　facility.facilityPhoto);
				$("#facility_purpose_edit").val(facility.purpose);
				$("#facility_bornDate_edit").val(facility.bornDate);
				$("#facility_fuzeren_edit").val(facility.fuzeren);
				$("#facility_telephone_edit").val(facility.telephone);
				$("#facility_facilityMemo_edit").val(facility.facilityMemo);
				$('#facilityEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除基础设施信息*/
function facilityDelete(facilityId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Facility/deletes",
			data : {
				facilityIds : facilityId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#facilityQueryForm").submit();
					//location.href= basePath + "Facility/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交基础设施信息表单给服务器端修改*/
function ajaxFacilityModify() {
	$.ajax({
		url :  basePath + "Facility/" + $("#facility_facilityId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#facilityEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#facilityQueryForm").submit();
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

    /*投放日期组件*/
    $('.facility_bornDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
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

