package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Violation {
    /*违规id*/
    private Integer violationId;
    public Integer getViolationId(){
        return violationId;
    }
    public void setViolationId(Integer violationId){
        this.violationId = violationId;
    }

    /*违规住户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*违规简述*/
    @NotEmpty(message="违规简述不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*违规详情*/
    @NotEmpty(message="违规详情不能为空")
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
    private String handleResult;
    public String getHandleResult() {
        return handleResult;
    }
    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    /*举报时间*/
    @NotEmpty(message="举报时间不能为空")
    private String uploadTime;
    public String getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    /*举报人*/
    private UserInfo reportUserObj;
    public UserInfo getReportUserObj() {
        return reportUserObj;
    }
    public void setReportUserObj(UserInfo reportUserObj) {
        this.reportUserObj = reportUserObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonViolation=new JSONObject(); 
		jsonViolation.accumulate("violationId", this.getViolationId());
		jsonViolation.accumulate("userObj", this.getUserObj().getRoomNo());
		jsonViolation.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonViolation.accumulate("title", this.getTitle());
		jsonViolation.accumulate("content", this.getContent());
		jsonViolation.accumulate("handleState", this.getHandleState());
		jsonViolation.accumulate("handleResult", this.getHandleResult());
		jsonViolation.accumulate("uploadTime", this.getUploadTime().length()>19?this.getUploadTime().substring(0,19):this.getUploadTime());
		jsonViolation.accumulate("reportUserObj", this.getReportUserObj().getRoomNo());
		jsonViolation.accumulate("reportUserObjPri", this.getReportUserObj().getUser_name());
		return jsonViolation;
    }}