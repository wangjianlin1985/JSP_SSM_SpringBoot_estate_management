package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.RepairClass;

public interface RepairClassMapper {
	/*添加报修分类信息*/
	public void addRepairClass(RepairClass repairClass) throws Exception;

	/*按照查询条件分页查询报修分类记录*/
	public ArrayList<RepairClass> queryRepairClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有报修分类记录*/
	public ArrayList<RepairClass> queryRepairClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的报修分类记录数*/
	public int queryRepairClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条报修分类记录*/
	public RepairClass getRepairClass(int repairClassId) throws Exception;

	/*更新报修分类记录*/
	public void updateRepairClass(RepairClass repairClass) throws Exception;

	/*删除报修分类记录*/
	public void deleteRepairClass(int repairClassId) throws Exception;

}
