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
@TableName("m_service_dep_desc")
@ApiModel(value = "MServiceDepDesc对象", description = "")
public class MServiceDepDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "service_id", type = IdType.AUTO)
    private Integer serviceId;

    private String name;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MServiceDepDesc{" +
            "serviceId=" + serviceId +
            ", name=" + name +
        "}";
    }
}
