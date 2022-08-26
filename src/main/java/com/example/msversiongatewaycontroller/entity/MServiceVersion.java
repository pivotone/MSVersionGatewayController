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
@TableName("m_service_version")
@ApiModel(value = "MServiceVersion对象", description = "")
public class MServiceVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer majorVersionNumber;

    private Integer minorVersionNumber;

    private Integer patchVersionNumber;

    private Integer serviceId;

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
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "MServiceVersion{" +
            "majorVersionNumber=" + majorVersionNumber +
            ", minorVersionNumber=" + minorVersionNumber +
            ", patchVersionNumber=" + patchVersionNumber +
            ", serviceId=" + serviceId +
        "}";
    }
}
