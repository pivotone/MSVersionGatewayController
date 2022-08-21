package com.example.msversiongatewaycontroller.model;

public class MServiceInterface {
    private Integer mServiceId;
    private String patternUrl;
    private String description;
    private String functionName;
    private String requestType;

    public Integer getMServiceId() {
        return mServiceId;
    }

    public void setMServiceId(Integer mServiceId) {
        this.mServiceId = mServiceId;
    }

    public String getPatternUrl() {
        return patternUrl;
    }

    public void setPatternUrl(String patternUrl) {
        this.patternUrl = patternUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }





    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }


}
