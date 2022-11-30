package com.example.msversiongatewaycontroller.mapper;

import com.example.msversiongatewaycontroller.entity.VersionInterval;
import com.example.msversiongatewaycontroller.entity.VersionMarker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author pivot
 * @since 2022-11-30
 */
@Mapper
public interface VersionMarkerMapper extends BaseMapper<VersionMarker> {
    VersionInterval callGetVersionInterval(@Param("major") int major, @Param("minor") int minor,
                                           @Param("patch") int patch, @Param("url") String url,
                                           @Param("requestType") String requestType,
                                           VersionInterval interval);
}
