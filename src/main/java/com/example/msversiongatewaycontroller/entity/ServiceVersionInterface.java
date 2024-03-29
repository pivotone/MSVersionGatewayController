package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@TableName("service_version_interface")
@ApiModel(value = "ServiceVersionInterface对象", description = "VIEW")
public class ServiceVersionInterface implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer serviceId;

    private String mServiceName;

    private String description;

    private Integer majorVersionNumber;

    private Integer minorVersionNumber;

    private Integer patchVersionNumber;

    private Integer versionId;

    private String patternUrl;

    private String functionDescription;

    private String functionName;

    private String requestType;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public String getmServiceName() {
        return mServiceName;
    }

    public void setmServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getMajorVersionNumber() {
        return majorVersionNumber;
    }

    public void setMajorVersionNumber(Integer majorVersionNumber) {
        this.majorVersionNumber = majorVersionNumber;
    }
    public Integer getMinorVersionNumber() {
        return minorVersionNumber;
    }

    public void setMinorVersionNumber(Integer minorVersionNumber) {
        this.minorVersionNumber = minorVersionNumber;
    }
    public Integer getPatchVersionNumber() {
        return patchVersionNumber;
    }

    public void setPatchVersionNumber(Integer patchVersionNumber) {
        this.patchVersionNumber = patchVersionNumber;
    }
    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }
    public String getPatternUrl() {
        return patternUrl;
    }

    public void setPatternUrl(String patternUrl) {
        this.patternUrl = patternUrl;
    }
    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
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

    @Override
    public String toString() {
        return "ServiceVersionInterface{" +
            "serviceId=" + serviceId +
            ", mServiceName=" + mServiceName +
            ", description=" + description +
            ", majorVersionNumber=" + majorVersionNumber +
            ", minorVersionNumber=" + minorVersionNumber +
            ", patchVersionNumber=" + patchVersionNumber +
            ", versionId=" + versionId +
            ", patternUrl=" + patternUrl +
            ", functionDescription=" + functionDescription +
            ", functionName=" + functionName +
            ", requestType=" + requestType +
        "}";
    }
}
