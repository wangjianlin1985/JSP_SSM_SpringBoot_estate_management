package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.FeeClass;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.Fee;

import com.chengxusheji.mapper.FeeMapper;
@Service
public class FeeService {

	@Resource FeeMapper feeMapper;
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

    /*添加住户缴费记录*/
    public void addFee(Fee fee) throws Exception {
    	feeMapper.addFee(fee);
    }

    /*按照查询条件分页查询住户缴费记录*/
    public ArrayList<Fee> queryFee(FeeClass feeClassObj,String year,String month,UserInfo userObj,String feeState,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != feeClassObj && feeClassObj.getClassId()!= null && feeClassObj.getClassId()!= 0)  where += " and t_fee.feeClassObj=" + feeClassObj.getClassId();
    	if(!year.equals("")) where = where + " and t_fee.year like '%" + year + "%'";
    	if(!month.equals("")) where = where + " and t_fee.month like '%" + month + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_fee.userObj='" + userObj.getUser_name() + "'";
    	if(!feeState.equals("")) where = where + " and t_fee.feeState like '%" + feeState + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return feeMapper.queryFee(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Fee> queryFee(FeeClass feeClassObj,String year,String month,UserInfo userObj,String feeState) throws Exception  { 
     	String where = "where 1=1";
    	if(null != feeClassObj && feeClassObj.getClassId()!= null && feeClassObj.getClassId()!= 0)  where += " and t_fee.feeClassObj=" + feeClassObj.getClassId();
    	if(!year.equals("")) where = where + " and t_fee.year like '%" + year + "%'";
    	if(!month.equals("")) where = where + " and t_fee.month like '%" + month + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_fee.userObj='" + userObj.getUser_name() + "'";
    	if(!feeState.equals("")) where = where + " and t_fee.feeState like '%" + feeState + "%'";
    	return feeMapper.queryFeeList(where);
    }

    /*查询所有住户缴费记录*/
    public ArrayList<Fee> queryAllFee()  throws Exception {
        return feeMapper.queryFeeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(FeeClass feeClassObj,String year,String month,UserInfo userObj,String feeState) throws Exception {
     	String where = "where 1=1";
    	if(null != feeClassObj && feeClassObj.getClassId()!= null && feeClassObj.getClassId()!= 0)  where += " and t_fee.feeClassObj=" + feeClassObj.getClassId();
    	if(!year.equals("")) where = where + " and t_fee.year like '%" + year + "%'";
    	if(!month.equals("")) where = where + " and t_fee.month like '%" + month + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_fee.userObj='" + userObj.getUser_name() + "'";
    	if(!feeState.equals("")) where = where + " and t_fee.feeState like '%" + feeState + "%'";
        recordNumber = feeMapper.queryFeeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取住户缴费记录*/
    public Fee getFee(int feeId) throws Exception  {
        Fee fee = feeMapper.getFee(feeId);
        return fee;
    }

    /*更新住户缴费记录*/
    public void updateFee(Fee fee) throws Exception {
        feeMapper.updateFee(fee);
    }

    /*删除一条住户缴费记录*/
    public void deleteFee (int feeId) throws Exception {
        feeMapper.deleteFee(feeId);
    }

    /*删除多条住户缴费信息*/
    public int deleteFees (String feeIds) throws Exception {
    	String _feeIds[] = feeIds.split(",");
    	for(String _feeId: _feeIds) {
    		feeMapper.deleteFee(Integer.parseInt(_feeId));
    	}
    	return _feeIds.length;
    }
}
