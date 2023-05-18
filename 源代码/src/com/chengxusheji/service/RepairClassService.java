package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.RepairClass;

import com.chengxusheji.mapper.RepairClassMapper;
@Service
public class RepairClassService {

	@Resource RepairClassMapper repairClassMapper;
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

    /*添加报修分类记录*/
    public void addRepairClass(RepairClass repairClass) throws Exception {
    	repairClassMapper.addRepairClass(repairClass);
    }

    /*按照查询条件分页查询报修分类记录*/
    public ArrayList<RepairClass> queryRepairClass(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return repairClassMapper.queryRepairClass(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<RepairClass> queryRepairClass() throws Exception  { 
     	String where = "where 1=1";
    	return repairClassMapper.queryRepairClassList(where);
    }

    /*查询所有报修分类记录*/
    public ArrayList<RepairClass> queryAllRepairClass()  throws Exception {
        return repairClassMapper.queryRepairClassList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = repairClassMapper.queryRepairClassCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取报修分类记录*/
    public RepairClass getRepairClass(int repairClassId) throws Exception  {
        RepairClass repairClass = repairClassMapper.getRepairClass(repairClassId);
        return repairClass;
    }

    /*更新报修分类记录*/
    public void updateRepairClass(RepairClass repairClass) throws Exception {
        repairClassMapper.updateRepairClass(repairClass);
    }

    /*删除一条报修分类记录*/
    public void deleteRepairClass (int repairClassId) throws Exception {
        repairClassMapper.deleteRepairClass(repairClassId);
    }

    /*删除多条报修分类信息*/
    public int deleteRepairClasss (String repairClassIds) throws Exception {
    	String _repairClassIds[] = repairClassIds.split(",");
    	for(String _repairClassId: _repairClassIds) {
    		repairClassMapper.deleteRepairClass(Integer.parseInt(_repairClassId));
    	}
    	return _repairClassIds.length;
    }
}
