package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@TableName("sys_route_conf")
@ApiModel(value = "SysRouteConf对象", description = "")
public class SysRouteConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String routeId;

    private String predicates;

    private String filters;

    private String uri;

    private Integer orders;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private Boolean delFlag;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }
    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "SysRouteConf{" +
            "id=" + id +
            ", routeId=" + routeId +
            ", predicates=" + predicates +
            ", filters=" + filters +
            ", uri=" + uri +
            ", order=" + orders +
            ", createTime=" + createTime +
            ", delFlag=" + delFlag +
            ", updateTime=" + updateTime +
        "}";
    }
}
