package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Facility;

import com.chengxusheji.mapper.FacilityMapper;
@Service
public class FacilityService {

	@Resource FacilityMapper facilityMapper;
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

    /*添加基础设施记录*/
    public void addFacility(Facility facility) throws Exception {
    	facilityMapper.addFacility(facility);
    }

    /*按照查询条件分页查询基础设施记录*/
    public ArrayList<Facility> queryFacility(String facilityName,String bornDate,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!facilityName.equals("")) where = where + " and t_facility.facilityName like '%" + facilityName + "%'";
    	if(!bornDate.equals("")) where = where + " and t_facility.bornDate like '%" + bornDate + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return facilityMapper.queryFacility(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Facility> queryFacility(String facilityName,String bornDate) throws Exception  { 
     	String where = "where 1=1";
    	if(!facilityName.equals("")) where = where + " and t_facility.facilityName like '%" + facilityName + "%'";
    	if(!bornDate.equals("")) where = where + " and t_facility.bornDate like '%" + bornDate + "%'";
    	return facilityMapper.queryFacilityList(where);
    }

    /*查询所有基础设施记录*/
    public ArrayList<Facility> queryAllFacility()  throws Exception {
        return facilityMapper.queryFacilityList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String facilityName,String bornDate) throws Exception {
     	String where = "where 1=1";
    	if(!facilityName.equals("")) where = where + " and t_facility.facilityName like '%" + facilityName + "%'";
    	if(!bornDate.equals("")) where = where + " and t_facility.bornDate like '%" + bornDate + "%'";
        recordNumber = facilityMapper.queryFacilityCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取基础设施记录*/
    public Facility getFacility(int facilityId) throws Exception  {
        Facility facility = facilityMapper.getFacility(facilityId);
        return facility;
    }

    /*更新基础设施记录*/
    public void updateFacility(Facility facility) throws Exception {
        facilityMapper.updateFacility(facility);
    }

    /*删除一条基础设施记录*/
    public void deleteFacility (int facilityId) throws Exception {
        facilityMapper.deleteFacility(facilityId);
    }

    /*删除多条基础设施信息*/
    public int deleteFacilitys (String facilityIds) throws Exception {
    	String _facilityIds[] = facilityIds.split(",");
    	for(String _facilityId: _facilityIds) {
    		facilityMapper.deleteFacility(Integer.parseInt(_facilityId));
    	}
    	return _facilityIds.length;
    }
}
