package com.example.msversiongatewaycontroller.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;
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

    private Blob predicates;

    private Blob filters;

    private String uri;

    private Integer order;

    private LocalDateTime createTime;

    private Boolean delFlag;

    private LocalDateTime updateTime;

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
    public Blob getPredicates() {
        return predicates;
    }

    public void setPredicates(byte[] predicates) {
        this.predicates = predicates;
    }
    public Blob getFilters() {
        return filters;
    }

    public void setFilters(byte[] filters) {
        this.filters = filters;
    }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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
            ", order=" + order +
            ", createTime=" + createTime +
            ", delFlag=" + delFlag +
            ", updateTime=" + updateTime +
        "}";
    }
}
