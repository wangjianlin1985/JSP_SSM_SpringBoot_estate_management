package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Facility {
    /*设施id*/
    private Integer facilityId;
    public Integer getFacilityId(){
        return facilityId;
    }
    public void setFacilityId(Integer facilityId){
        this.facilityId = facilityId;
    }

    /*设施名称*/
    @NotEmpty(message="设施名称不能为空")
    private String facilityName;
    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    /*设施照片*/
    private String facilityPhoto;
    public String getFacilityPhoto() {
        return facilityPhoto;
    }
    public void setFacilityPhoto(String facilityPhoto) {
        this.facilityPhoto = facilityPhoto;
    }

    /*设施用途*/
    @NotEmpty(message="设施用途不能为空")
    private String purpose;
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /*投放日期*/
    @NotEmpty(message="投放日期不能为空")
    private String bornDate;
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    /*负责人*/
    @NotEmpty(message="负责人不能为空")
    private String fuzeren;
    public String getFuzeren() {
        return fuzeren;
    }
    public void setFuzeren(String fuzeren) {
        this.fuzeren = fuzeren;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*设施备注*/
    private String facilityMemo;
    public String getFacilityMemo() {
        return facilityMemo;
    }
    public void setFacilityMemo(String facilityMemo) {
        this.facilityMemo = facilityMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonFacility=new JSONObject(); 
		jsonFacility.accumulate("facilityId", this.getFacilityId());
		jsonFacility.accumulate("facilityName", this.getFacilityName());
		jsonFacility.accumulate("facilityPhoto", this.getFacilityPhoto());
		jsonFacility.accumulate("purpose", this.getPurpose());
		jsonFacility.accumulate("bornDate", this.getBornDate().length()>19?this.getBornDate().substring(0,19):this.getBornDate());
		jsonFacility.accumulate("fuzeren", this.getFuzeren());
		jsonFacility.accumulate("telephone", this.getTelephone());
		jsonFacility.accumulate("facilityMemo", this.getFacilityMemo());
		return jsonFacility;
    }}