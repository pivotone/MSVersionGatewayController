package com.example.msversiongatewaycontroller.mapper;

import com.example.msversiongatewaycontroller.entity.VersionMarker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author pivot
 * @since 2022-11-30
 */
@Mapper
@Repository
public interface VersionMarkerMapper extends BaseMapper<VersionMarker> {
    String callGetVersionInterval(Map<String, Object> params);
}
