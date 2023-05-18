package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.ViolationService;
import com.chengxusheji.po.Violation;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Violation管理控制层
@Controller
@RequestMapping("/Violation")
public class ViolationController extends BaseController {

    /*业务层对象*/
    @Resource ViolationService violationService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("reportUserObj")
	public void initBinderreportUserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reportUserObj.");
	}
	@InitBinder("violation")
	public void initBinderViolation(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("violation.");
	}
	/*跳转到添加Violation视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Violation());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Violation_add";
	}

	/*客户端ajax方式提交添加违规现象信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Violation violation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        violationService.addViolation(violation);
        message = "违规现象添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加违规现象信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Violation violation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		violation.setUploadTime(sdf.format(new java.util.Date()));
		
		String user_name = session.getAttribute("user_name").toString();
		UserInfo reportUserObj = new UserInfo();
		reportUserObj.setUser_name(user_name);
		violation.setReportUserObj(reportUserObj);
		
        violationService.addViolation(violation);
        message = "违规现象添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询违规现象信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("userObj") UserInfo userObj,String title,String handleState,String uploadTime,@ModelAttribute("reportUserObj") UserInfo reportUserObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		if(rows != 0)violationService.setRows(rows);
		List<Violation> violationList = violationService.queryViolation(userObj, title, handleState, uploadTime, reportUserObj, page);
	    /*计算总的页数和总的记录数*/
	    violationService.queryTotalPageAndRecordNumber(userObj, title, handleState, uploadTime, reportUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = violationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = violationService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Violation violation:violationList) {
			JSONObject jsonViolation = violation.getJsonObject();
			jsonArray.put(jsonViolation);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询违规现象信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Violation> violationList = violationService.queryAllViolation();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Violation violation:violationList) {
			JSONObject jsonViolation = new JSONObject();
			jsonViolation.accumulate("violationId", violation.getViolationId());
			jsonViolation.accumulate("title", violation.getTitle());
			jsonArray.put(jsonViolation);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询违规现象信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("userObj") UserInfo userObj,String title,String handleState,String uploadTime,@ModelAttribute("reportUserObj") UserInfo reportUserObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		List<Violation> violationList = violationService.queryViolation(userObj, title, handleState, uploadTime, reportUserObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    violationService.queryTotalPageAndRecordNumber(userObj, title, handleState, uploadTime, reportUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = violationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = violationService.getRecordNumber();
	    request.setAttribute("violationList",  violationList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("title", title);
	    request.setAttribute("handleState", handleState);
	    request.setAttribute("uploadTime", uploadTime);
	    request.setAttribute("reportUserObj", reportUserObj);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Violation/violation_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询违规现象信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("userObj") UserInfo userObj,String title,String handleState,String uploadTime,@ModelAttribute("reportUserObj") UserInfo reportUserObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		reportUserObj = new UserInfo();
		reportUserObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<Violation> violationList = violationService.queryViolation(userObj, title, handleState, uploadTime, reportUserObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    violationService.queryTotalPageAndRecordNumber(userObj, title, handleState, uploadTime, reportUserObj);
	    /*获取到总的页码数目*/
	    int totalPage = violationService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = violationService.getRecordNumber();
	    request.setAttribute("violationList",  violationList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("title", title);
	    request.setAttribute("handleState", handleState);
	    request.setAttribute("uploadTime", uploadTime);
	    request.setAttribute("reportUserObj", reportUserObj);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Violation/violation_userFrontquery_result"; 
	}

	

     /*前台查询Violation信息*/
	@RequestMapping(value="/{violationId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer violationId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键violationId获取Violation对象*/
        Violation violation = violationService.getViolation(violationId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("violation",  violation);
        return "Violation/violation_frontshow";
	}

	/*ajax方式显示违规现象修改jsp视图页*/
	@RequestMapping(value="/{violationId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer violationId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键violationId获取Violation对象*/
        Violation violation = violationService.getViolation(violationId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonViolation = violation.getJsonObject();
		out.println(jsonViolation.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新违规现象信息*/
	@RequestMapping(value = "/{violationId}/update", method = RequestMethod.POST)
	public void update(@Validated Violation violation, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			violationService.updateViolation(violation);
			message = "违规现象更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "违规现象更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除违规现象信息*/
	@RequestMapping(value="/{violationId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer violationId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  violationService.deleteViolation(violationId);
	            request.setAttribute("message", "违规现象删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "违规现象删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条违规现象记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String violationIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = violationService.deleteViolations(violationIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出违规现象信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("userObj") UserInfo userObj,String title,String handleState,String uploadTime,@ModelAttribute("reportUserObj") UserInfo reportUserObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(handleState == null) handleState = "";
        if(uploadTime == null) uploadTime = "";
        List<Violation> violationList = violationService.queryViolation(userObj,title,handleState,uploadTime,reportUserObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Violation信息记录"; 
        String[] headers = { "违规id","违规住户","违规简述","处理状态","举报时间","举报人"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<violationList.size();i++) {
        	Violation violation = violationList.get(i); 
        	dataset.add(new String[]{violation.getViolationId() + "",violation.getUserObj().getRoomNo(),violation.getTitle(),violation.getHandleState(),violation.getUploadTime(),violation.getReportUserObj().getRoomNo()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Violation.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
