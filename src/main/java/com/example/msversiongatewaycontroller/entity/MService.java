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
 * @since 2022-09-12
 */
@TableName("m_service")
@ApiModel(value = "MService对象", description = "")
public class MService implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mServiceName;

    private String description;

    @TableId(value = "service_id", type = IdType.AUTO)
    private Integer serviceId;

    private Integer health;

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
    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "MService{" +
            "mServiceName=" + mServiceName +
            ", description=" + description +
            ", serviceId=" + serviceId +
        "}";
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
}
