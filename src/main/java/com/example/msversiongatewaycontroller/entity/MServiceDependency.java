package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@TableName("m_service_dependency")
@ApiModel(value = "MServiceDependency对象", description = "")
public class MServiceDependency implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer serviceId;

    private Integer interfaceId;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public Integer getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }

    @Override
    public String toString() {
        return "MServiceDependency{" +
            "serviceId=" + serviceId +
            ", interfaceId=" + interfaceId +
        "}";
    }
}
