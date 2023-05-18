package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Fee;

public interface FeeMapper {
	/*添加住户缴费信息*/
	public void addFee(Fee fee) throws Exception;

	/*按照查询条件分页查询住户缴费记录*/
	public ArrayList<Fee> queryFee(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有住户缴费记录*/
	public ArrayList<Fee> queryFeeList(@Param("where") String where) throws Exception;

	/*按照查询条件的住户缴费记录数*/
	public int queryFeeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条住户缴费记录*/
	public Fee getFee(int feeId) throws Exception;

	/*更新住户缴费记录*/
	public void updateFee(Fee fee) throws Exception;

	/*删除住户缴费记录*/
	public void deleteFee(int feeId) throws Exception;

}
