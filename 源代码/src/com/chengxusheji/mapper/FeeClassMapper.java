package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.FeeClass;

public interface FeeClassMapper {
	/*添加费用类别信息*/
	public void addFeeClass(FeeClass feeClass) throws Exception;

	/*按照查询条件分页查询费用类别记录*/
	public ArrayList<FeeClass> queryFeeClass(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有费用类别记录*/
	public ArrayList<FeeClass> queryFeeClassList(@Param("where") String where) throws Exception;

	/*按照查询条件的费用类别记录数*/
	public int queryFeeClassCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条费用类别记录*/
	public FeeClass getFeeClass(int classId) throws Exception;

	/*更新费用类别记录*/
	public void updateFeeClass(FeeClass feeClass) throws Exception;

	/*删除费用类别记录*/
	public void deleteFeeClass(int classId) throws Exception;

}
