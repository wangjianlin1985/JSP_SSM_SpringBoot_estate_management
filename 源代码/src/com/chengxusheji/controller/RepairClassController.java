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
import com.chengxusheji.service.RepairClassService;
import com.chengxusheji.po.RepairClass;

//RepairClass管理控制层
@Controller
@RequestMapping("/RepairClass")
public class RepairClassController extends BaseController {

    /*业务层对象*/
    @Resource RepairClassService repairClassService;

	@InitBinder("repairClass")
	public void initBinderRepairClass(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repairClass.");
	}
	/*跳转到添加RepairClass视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new RepairClass());
		return "RepairClass_add";
	}

	/*客户端ajax方式提交添加报修分类信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated RepairClass repairClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        repairClassService.addRepairClass(repairClass);
        message = "报修分类添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询报修分类信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)repairClassService.setRows(rows);
		List<RepairClass> repairClassList = repairClassService.queryRepairClass(page);
	    /*计算总的页数和总的记录数*/
	    repairClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = repairClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairClassService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(RepairClass repairClass:repairClassList) {
			JSONObject jsonRepairClass = repairClass.getJsonObject();
			jsonArray.put(jsonRepairClass);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询报修分类信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<RepairClass> repairClassList = repairClassService.queryAllRepairClass();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(RepairClass repairClass:repairClassList) {
			JSONObject jsonRepairClass = new JSONObject();
			jsonRepairClass.accumulate("repairClassId", repairClass.getRepairClassId());
			jsonRepairClass.accumulate("repairClassName", repairClass.getRepairClassName());
			jsonArray.put(jsonRepairClass);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询报修分类信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<RepairClass> repairClassList = repairClassService.queryRepairClass(currentPage);
	    /*计算总的页数和总的记录数*/
	    repairClassService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = repairClassService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairClassService.getRecordNumber();
	    request.setAttribute("repairClassList",  repairClassList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "RepairClass/repairClass_frontquery_result"; 
	}

     /*前台查询RepairClass信息*/
	@RequestMapping(value="/{repairClassId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer repairClassId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键repairClassId获取RepairClass对象*/
        RepairClass repairClass = repairClassService.getRepairClass(repairClassId);

        request.setAttribute("repairClass",  repairClass);
        return "RepairClass/repairClass_frontshow";
	}

	/*ajax方式显示报修分类修改jsp视图页*/
	@RequestMapping(value="/{repairClassId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer repairClassId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键repairClassId获取RepairClass对象*/
        RepairClass repairClass = repairClassService.getRepairClass(repairClassId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonRepairClass = repairClass.getJsonObject();
		out.println(jsonRepairClass.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新报修分类信息*/
	@RequestMapping(value = "/{repairClassId}/update", method = RequestMethod.POST)
	public void update(@Validated RepairClass repairClass, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			repairClassService.updateRepairClass(repairClass);
			message = "报修分类更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "报修分类更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除报修分类信息*/
	@RequestMapping(value="/{repairClassId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer repairClassId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  repairClassService.deleteRepairClass(repairClassId);
	            request.setAttribute("message", "报修分类删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "报修分类删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条报修分类记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String repairClassIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = repairClassService.deleteRepairClasss(repairClassIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出报修分类信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<RepairClass> repairClassList = repairClassService.queryRepairClass();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "RepairClass信息记录"; 
        String[] headers = { "报修分类id","报修分类名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairClassList.size();i++) {
        	RepairClass repairClass = repairClassList.get(i); 
        	dataset.add(new String[]{repairClass.getRepairClassId() + "",repairClass.getRepairClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RepairClass.xls");//filename是下载的xls的名，建议最好用英文 
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
