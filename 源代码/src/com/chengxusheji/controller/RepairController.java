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
import com.chengxusheji.service.RepairService;
import com.chengxusheji.po.Repair;
import com.chengxusheji.service.RepairClassService;
import com.chengxusheji.po.RepairClass;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//Repair管理控制层
@Controller
@RequestMapping("/Repair")
public class RepairController extends BaseController {

    /*业务层对象*/
    @Resource RepairService repairService;

    @Resource RepairClassService repairClassService;
    @Resource UserInfoService userInfoService;
	@InitBinder("repairClassObj")
	public void initBinderrepairClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repairClassObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("repair")
	public void initBinderRepair(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repair.");
	}
	/*跳转到添加Repair视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Repair());
		/*查询所有的RepairClass信息*/
		List<RepairClass> repairClassList = repairClassService.queryAllRepairClass();
		request.setAttribute("repairClassList", repairClassList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "Repair_add";
	}

	/*客户端ajax方式提交添加住户报修信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Repair repair, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        repairService.addRepair(repair);
        message = "住户报修添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加住户报修信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(Repair repair, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		 
		String user_name = (String) session.getAttribute("user_name");
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(user_name);
		repair.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		repair.setUploadTime(sdf.format(new java.util.Date()));
		
        repairService.addRepair(repair);
        message = "住户报修添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询住户报修信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("repairClassObj") RepairClass repairClassObj,String title,String handleState,String uploadTime,@ModelAttribute("userObj") UserInfo userObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		if(rows != 0)repairService.setRows(rows);
		List<Repair> repairList = repairService.queryRepair(repairClassObj, title, handleState, uploadTime, userObj, page);
	    /*计算总的页数和总的记录数*/
	    repairService.queryTotalPageAndRecordNumber(repairClassObj, title, handleState, uploadTime, userObj);
	    /*获取到总的页码数目*/
	    int totalPage = repairService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Repair repair:repairList) {
			JSONObject jsonRepair = repair.getJsonObject();
			jsonArray.put(jsonRepair);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询住户报修信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Repair> repairList = repairService.queryAllRepair();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Repair repair:repairList) {
			JSONObject jsonRepair = new JSONObject();
			jsonRepair.accumulate("repairId", repair.getRepairId());
			jsonRepair.accumulate("title", repair.getTitle());
			jsonArray.put(jsonRepair);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询住户报修信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("repairClassObj") RepairClass repairClassObj,String title,String handleState,String uploadTime,@ModelAttribute("userObj") UserInfo userObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		List<Repair> repairList = repairService.queryRepair(repairClassObj, title, handleState, uploadTime, userObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    repairService.queryTotalPageAndRecordNumber(repairClassObj, title, handleState, uploadTime, userObj);
	    /*获取到总的页码数目*/
	    int totalPage = repairService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairService.getRecordNumber();
	    request.setAttribute("repairList",  repairList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("repairClassObj", repairClassObj);
	    request.setAttribute("title", title);
	    request.setAttribute("handleState", handleState);
	    request.setAttribute("uploadTime", uploadTime);
	    request.setAttribute("userObj", userObj);
	    List<RepairClass> repairClassList = repairClassService.queryAllRepairClass();
	    request.setAttribute("repairClassList", repairClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Repair/repair_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询住户报修信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("repairClassObj") RepairClass repairClassObj,String title,String handleState,String uploadTime,@ModelAttribute("userObj") UserInfo userObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (handleState == null) handleState = "";
		if (uploadTime == null) uploadTime = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		List<Repair> repairList = repairService.queryRepair(repairClassObj, title, handleState, uploadTime, userObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    repairService.queryTotalPageAndRecordNumber(repairClassObj, title, handleState, uploadTime, userObj);
	    /*获取到总的页码数目*/
	    int totalPage = repairService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairService.getRecordNumber();
	    request.setAttribute("repairList",  repairList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("repairClassObj", repairClassObj);
	    request.setAttribute("title", title);
	    request.setAttribute("handleState", handleState);
	    request.setAttribute("uploadTime", uploadTime);
	    request.setAttribute("userObj", userObj);
	    List<RepairClass> repairClassList = repairClassService.queryAllRepairClass();
	    request.setAttribute("repairClassList", repairClassList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "Repair/repair_userFrontquery_result"; 
	}
	
	

     /*前台查询Repair信息*/
	@RequestMapping(value="/{repairId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer repairId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键repairId获取Repair对象*/
        Repair repair = repairService.getRepair(repairId);

        List<RepairClass> repairClassList = repairClassService.queryAllRepairClass();
        request.setAttribute("repairClassList", repairClassList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("repair",  repair);
        return "Repair/repair_frontshow";
	}

	/*ajax方式显示住户报修修改jsp视图页*/
	@RequestMapping(value="/{repairId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer repairId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键repairId获取Repair对象*/
        Repair repair = repairService.getRepair(repairId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonRepair = repair.getJsonObject();
		out.println(jsonRepair.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新住户报修信息*/
	@RequestMapping(value = "/{repairId}/update", method = RequestMethod.POST)
	public void update(@Validated Repair repair, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			repairService.updateRepair(repair);
			message = "住户报修更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "住户报修更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除住户报修信息*/
	@RequestMapping(value="/{repairId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer repairId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  repairService.deleteRepair(repairId);
	            request.setAttribute("message", "住户报修删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "住户报修删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条住户报修记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String repairIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = repairService.deleteRepairs(repairIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出住户报修信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("repairClassObj") RepairClass repairClassObj,String title,String handleState,String uploadTime,@ModelAttribute("userObj") UserInfo userObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(handleState == null) handleState = "";
        if(uploadTime == null) uploadTime = "";
        List<Repair> repairList = repairService.queryRepair(repairClassObj,title,handleState,uploadTime,userObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Repair信息记录"; 
        String[] headers = { "报修id","报修分类","问题简述","处理状态","上报时间","上报住户"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairList.size();i++) {
        	Repair repair = repairList.get(i); 
        	dataset.add(new String[]{repair.getRepairId() + "",repair.getRepairClassObj().getRepairClassName(),repair.getTitle(),repair.getHandleState(),repair.getUploadTime(),repair.getUserObj().getRoomNo()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Repair.xls");//filename是下载的xls的名，建议最好用英文 
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
