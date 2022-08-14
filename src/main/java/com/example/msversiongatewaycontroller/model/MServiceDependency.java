package com.example.msversiongatewaycontroller.model;

import java.util.Set;

public class MServiceDependency {
    private String serviceName;
    private String functionName;
    private String patternUrl;
    private Set<MServiceVersion> versionSet;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getPatternUrl() {
        return patternUrl;
    }

    public void setPatternUrl(String patternUrl) {
        this.patternUrl = patternUrl;
    }

    public Set<MServiceVersion> getVersionSet() {
        return versionSet;
    }

    public void setVersionSet(Set<MServiceVersion> versionSet) {
        this.versionSet = versionSet;
    }

}
