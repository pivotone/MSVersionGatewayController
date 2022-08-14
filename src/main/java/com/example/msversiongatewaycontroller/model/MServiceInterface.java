package com.example.msversiongatewaycontroller.model;

public class MServiceInterface {
    private String MServiceId;
    private String PatternUrl;
    private String Description;
    private String functionName;

    public String getMServiceId() {
        return MServiceId;
    }

    public void setMServiceId(String MServiceId) {
        this.MServiceId = MServiceId;
    }

    public String getPatternUrl() {
        return PatternUrl;
    }

    public void setPatternUrl(String patternUrl) {
        PatternUrl = patternUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    private String requestType;
}
