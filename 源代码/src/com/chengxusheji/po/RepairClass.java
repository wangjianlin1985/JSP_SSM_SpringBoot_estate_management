package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class RepairClass {
    /*报修分类id*/
    private Integer repairClassId;
    public Integer getRepairClassId(){
        return repairClassId;
    }
    public void setRepairClassId(Integer repairClassId){
        this.repairClassId = repairClassId;
    }

    /*报修分类名称*/
    @NotEmpty(message="报修分类名称不能为空")
    private String repairClassName;
    public String getRepairClassName() {
        return repairClassName;
    }
    public void setRepairClassName(String repairClassName) {
        this.repairClassName = repairClassName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRepairClass=new JSONObject(); 
		jsonRepairClass.accumulate("repairClassId", this.getRepairClassId());
		jsonRepairClass.accumulate("repairClassName", this.getRepairClassName());
		return jsonRepairClass;
    }}