package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Building {
    /*楼栋id*/
    private Integer buildingId;
    public Integer getBuildingId(){
        return buildingId;
    }
    public void setBuildingId(Integer buildingId){
        this.buildingId = buildingId;
    }

    /*楼栋名称*/
    @NotEmpty(message="楼栋名称不能为空")
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*楼栋备注*/
    private String buildingMemo;
    public String getBuildingMemo() {
        return buildingMemo;
    }
    public void setBuildingMemo(String buildingMemo) {
        this.buildingMemo = buildingMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBuilding=new JSONObject(); 
		jsonBuilding.accumulate("buildingId", this.getBuildingId());
		jsonBuilding.accumulate("buildingName", this.getBuildingName());
		jsonBuilding.accumulate("buildingMemo", this.getBuildingMemo());
		return jsonBuilding;
    }}