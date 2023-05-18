package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Violation;

import com.chengxusheji.mapper.ViolationMapper;
@Service
public class ViolationService {

	@Resource ViolationMapper violationMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加违规现象记录*/
    public void addViolation(Violation violation) throws Exception {
    	violationMapper.addViolation(violation);
    }

    /*按照查询条件分页查询违规现象记录*/
    public ArrayList<Violation> queryViolation(UserInfo userObj,String title,String handleState,String uploadTime,UserInfo reportUserObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_violation.userObj='" + userObj.getUser_name() + "'";
    	if(!title.equals("")) where = where + " and t_violation.title like '%" + title + "%'";
    	if(!handleState.equals("")) where = where + " and t_violation.handleState like '%" + handleState + "%'";
    	if(!uploadTime.equals("")) where = where + " and t_violation.uploadTime like '%" + uploadTime + "%'";
    	if(null != reportUserObj &&  reportUserObj.getUser_name() != null  && !reportUserObj.getUser_name().equals(""))  where += " and t_violation.reportUserObj='" + reportUserObj.getUser_name() + "'";
    	int startIndex = (currentPage-1) * this.rows;
    	return violationMapper.queryViolation(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Violation> queryViolation(UserInfo userObj,String title,String handleState,String uploadTime,UserInfo reportUserObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_violation.userObj='" + userObj.getUser_name() + "'";
    	if(!title.equals("")) where = where + " and t_violation.title like '%" + title + "%'";
    	if(!handleState.equals("")) where = where + " and t_violation.handleState like '%" + handleState + "%'";
    	if(!uploadTime.equals("")) where = where + " and t_violation.uploadTime like '%" + uploadTime + "%'";
    	if(null != reportUserObj &&  reportUserObj.getUser_name() != null && !reportUserObj.getUser_name().equals(""))  where += " and t_violation.reportUserObj='" + reportUserObj.getUser_name() + "'";
    	return violationMapper.queryViolationList(where);
    }

    /*查询所有违规现象记录*/
    public ArrayList<Violation> queryAllViolation()  throws Exception {
        return violationMapper.queryViolationList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(UserInfo userObj,String title,String handleState,String uploadTime,UserInfo reportUserObj) throws Exception {
     	String where = "where 1=1";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_violation.userObj='" + userObj.getUser_name() + "'";
    	if(!title.equals("")) where = where + " and t_violation.title like '%" + title + "%'";
    	if(!handleState.equals("")) where = where + " and t_violation.handleState like '%" + handleState + "%'";
    	if(!uploadTime.equals("")) where = where + " and t_violation.uploadTime like '%" + uploadTime + "%'";
    	if(null != reportUserObj &&  reportUserObj.getUser_name() != null && !reportUserObj.getUser_name().equals(""))  where += " and t_violation.reportUserObj='" + reportUserObj.getUser_name() + "'";
        recordNumber = violationMapper.queryViolationCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取违规现象记录*/
    public Violation getViolation(int violationId) throws Exception  {
        Violation violation = violationMapper.getViolation(violationId);
        return violation;
    }

    /*更新违规现象记录*/
    public void updateViolation(Violation violation) throws Exception {
        violationMapper.updateViolation(violation);
    }

    /*删除一条违规现象记录*/
    public void deleteViolation (int violationId) throws Exception {
        violationMapper.deleteViolation(violationId);
    }

    /*删除多条违规现象信息*/
    public int deleteViolations (String violationIds) throws Exception {
    	String _violationIds[] = violationIds.split(",");
    	for(String _violationId: _violationIds) {
    		violationMapper.deleteViolation(Integer.parseInt(_violationId));
    	}
    	return _violationIds.length;
    }
}
