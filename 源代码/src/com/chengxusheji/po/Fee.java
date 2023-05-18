package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Fee {
    /*缴费id*/
    private Integer feeId;
    public Integer getFeeId(){
        return feeId;
    }
    public void setFeeId(Integer feeId){
        this.feeId = feeId;
    }

    /*费用类别*/
    private FeeClass feeClassObj;
    public FeeClass getFeeClassObj() {
        return feeClassObj;
    }
    public void setFeeClassObj(FeeClass feeClassObj) {
        this.feeClassObj = feeClassObj;
    }

    /*费用年份*/
    @NotEmpty(message="费用年份不能为空")
    private String year;
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }

    /*费用月份*/
    @NotEmpty(message="费用月份不能为空")
    private String month;
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }

    /*使用量*/
    @NotEmpty(message="使用量不能为空")
    private String userCount;
    public String getUserCount() {
        return userCount;
    }
    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    /*应缴金额*/
    @NotNull(message="必须输入应缴金额")
    private Float feeValue;
    public Float getFeeValue() {
        return feeValue;
    }
    public void setFeeValue(Float feeValue) {
        this.feeValue = feeValue;
    }

    /*缴费住户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*缴费状态*/
    @NotEmpty(message="缴费状态不能为空")
    private String feeState;
    public String getFeeState() {
        return feeState;
    }
    public void setFeeState(String feeState) {
        this.feeState = feeState;
    }

    /*缴费备注*/
    private String feeMemo;
    public String getFeeMemo() {
        return feeMemo;
    }
    public void setFeeMemo(String feeMemo) {
        this.feeMemo = feeMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonFee=new JSONObject(); 
		jsonFee.accumulate("feeId", this.getFeeId());
		jsonFee.accumulate("feeClassObj", this.getFeeClassObj().getClassName());
		jsonFee.accumulate("feeClassObjPri", this.getFeeClassObj().getClassId());
		jsonFee.accumulate("year", this.getYear());
		jsonFee.accumulate("month", this.getMonth());
		jsonFee.accumulate("userCount", this.getUserCount());
		jsonFee.accumulate("feeValue", this.getFeeValue());
		jsonFee.accumulate("userObj", this.getUserObj().getRoomNo());
		jsonFee.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonFee.accumulate("feeState", this.getFeeState());
		jsonFee.accumulate("feeMemo", this.getFeeMemo());
		return jsonFee;
    }}