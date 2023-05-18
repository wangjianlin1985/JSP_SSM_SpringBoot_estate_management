package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Repair {
    /*报修id*/
    private Integer repairId;
    public Integer getRepairId(){
        return repairId;
    }
    public void setRepairId(Integer repairId){
        this.repairId = repairId;
    }

    /*报修分类*/
    private RepairClass repairClassObj;
    public RepairClass getRepairClassObj() {
        return repairClassObj;
    }
    public void setRepairClassObj(RepairClass repairClassObj) {
        this.repairClassObj = repairClassObj;
    }

    /*问题简述*/
    @NotEmpty(message="问题简述不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*问题详情*/
    @NotEmpty(message="问题详情不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*处理状态*/
    @NotEmpty(message="处理状态不能为空")
    private String handleState;
    public String getHandleState() {
        return handleState;
    }
    public void setHandleState(String handleState) {
        this.handleState = handleState;
    }

    /*处理结果*/
    @NotEmpty(message="处理结果不能为空")
    private String handResult;
    public String getHandResult() {
        return handResult;
    }
    public void setHandResult(String handResult) {
        this.handResult = handResult;
    }

    /*上报时间*/
    @NotEmpty(message="上报时间不能为空")
    private String uploadTime;
    public String getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    /*上报住户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRepair=new JSONObject(); 
		jsonRepair.accumulate("repairId", this.getRepairId());
		jsonRepair.accumulate("repairClassObj", this.getRepairClassObj().getRepairClassName());
		jsonRepair.accumulate("repairClassObjPri", this.getRepairClassObj().getRepairClassId());
		jsonRepair.accumulate("title", this.getTitle());
		jsonRepair.accumulate("content", this.getContent());
		jsonRepair.accumulate("handleState", this.getHandleState());
		jsonRepair.accumulate("handResult", this.getHandResult());
		jsonRepair.accumulate("uploadTime", this.getUploadTime().length()>19?this.getUploadTime().substring(0,19):this.getUploadTime());
		jsonRepair.accumulate("userObj", this.getUserObj().getRoomNo());
		jsonRepair.accumulate("userObjPri", this.getUserObj().getUser_name());
		return jsonRepair;
    }}