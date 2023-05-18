package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.FeeClass;

import com.chengxusheji.mapper.FeeClassMapper;
@Service
public class FeeClassService {

	@Resource FeeClassMapper feeClassMapper;
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

    /*添加费用类别记录*/
    public void addFeeClass(FeeClass feeClass) throws Exception {
    	feeClassMapper.addFeeClass(feeClass);
    }

    /*按照查询条件分页查询费用类别记录*/
    public ArrayList<FeeClass> queryFeeClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return feeClassMapper.queryFeeClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<FeeClass> queryFeeClass() throws Exception  { 
     	String where = "where 1=1";
    	return feeClassMapper.queryFeeClassList(where);
    }

    /*查询所有费用类别记录*/
    public ArrayList<FeeClass> queryAllFeeClass()  throws Exception {
        return feeClassMapper.queryFeeClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = feeClassMapper.queryFeeClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取费用类别记录*/
    public FeeClass getFeeClass(int classId) throws Exception  {
        FeeClass feeClass = feeClassMapper.getFeeClass(classId);
        return feeClass;
    }

    /*更新费用类别记录*/
    public void updateFeeClass(FeeClass feeClass) throws Exception {
        feeClassMapper.updateFeeClass(feeClass);
    }

    /*删除一条费用类别记录*/
    public void deleteFeeClass (int classId) throws Exception {
        feeClassMapper.deleteFeeClass(classId);
    }

    /*删除多条费用类别信息*/
    public int deleteFeeClasss (String classIds) throws Exception {
    	String _classIds[] = classIds.split(",");
    	for(String _classId: _classIds) {
    		feeClassMapper.deleteFeeClass(Integer.parseInt(_classId));
    	}
    	return _classIds.length;
    }
}
