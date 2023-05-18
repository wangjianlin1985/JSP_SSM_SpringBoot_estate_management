package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Violation;

public interface ViolationMapper {
	/*添加违规现象信息*/
	public void addViolation(Violation violation) throws Exception;

	/*按照查询条件分页查询违规现象记录*/
	public ArrayList<Violation> queryViolation(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有违规现象记录*/
	public ArrayList<Violation> queryViolationList(@Param("where") String where) throws Exception;

	/*按照查询条件的违规现象记录数*/
	public int queryViolationCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条违规现象记录*/
	public Violation getViolation(int violationId) throws Exception;

	/*更新违规现象记录*/
	public void updateViolation(Violation violation) throws Exception;

	/*删除违规现象记录*/
	public void deleteViolation(int violationId) throws Exception;

}
