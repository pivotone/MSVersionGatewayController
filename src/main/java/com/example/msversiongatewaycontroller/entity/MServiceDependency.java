package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author pivot
 * @since 2022-08-26
 */
@TableName("m_service_dependency")
@ApiModel(value = "MServiceDependency对象", description = "")
public class MServiceDependency implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serviceName;

    private String functionName;

    private String patternUrl;

    private Integer serviceId;

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
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "MServiceDependency{" +
            "serviceName=" + serviceName +
            ", functionName=" + functionName +
            ", patternUrl=" + patternUrl +
            ", serviceId=" + serviceId +
        "}";
    }
}
