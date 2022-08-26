package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("m_service_interface")
@ApiModel(value = "MServiceInterface对象", description = "")
public class MServiceInterface implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer serviceId;

    private String patternUrl;

    private String description;

    private String functionName;

    private String requestType;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MServiceInterface{" +
            "serviceId=" + serviceId +
            ", patternUrl=" + patternUrl +
            ", description=" + description +
            ", functionName=" + functionName +
            ", requestType=" + requestType +
            ", id=" + id +
        "}";
    }
}
