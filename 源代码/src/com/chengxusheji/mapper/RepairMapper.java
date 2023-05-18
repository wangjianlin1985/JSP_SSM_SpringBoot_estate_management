package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Repair;

public interface RepairMapper {
	/*添加住户报修信息*/
	public void addRepair(Repair repair) throws Exception;

	/*按照查询条件分页查询住户报修记录*/
	public ArrayList<Repair> queryRepair(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有住户报修记录*/
	public ArrayList<Repair> queryRepairList(@Param("where") String where) throws Exception;

	/*按照查询条件的住户报修记录数*/
	public int queryRepairCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条住户报修记录*/
	public Repair getRepair(int repairId) throws Exception;

	/*更新住户报修记录*/
	public void updateRepair(Repair repair) throws Exception;

	/*删除住户报修记录*/
	public void deleteRepair(int repairId) throws Exception;

}
