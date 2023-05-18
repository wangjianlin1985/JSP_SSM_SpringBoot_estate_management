package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Facility;

public interface FacilityMapper {
	/*添加基础设施信息*/
	public void addFacility(Facility facility) throws Exception;

	/*按照查询条件分页查询基础设施记录*/
	public ArrayList<Facility> queryFacility(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有基础设施记录*/
	public ArrayList<Facility> queryFacilityList(@Param("where") String where) throws Exception;

	/*按照查询条件的基础设施记录数*/
	public int queryFacilityCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条基础设施记录*/
	public Facility getFacility(int facilityId) throws Exception;

	/*更新基础设施记录*/
	public void updateFacility(Facility facility) throws Exception;

	/*删除基础设施记录*/
	public void deleteFacility(int facilityId) throws Exception;

}
