package com.example.msversiongatewaycontroller.mapper;

import com.example.msversiongatewaycontroller.entity.MServiceVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@Mapper
public interface MServiceVersionMapper extends BaseMapper<MServiceVersion> {

    MServiceVersion selectByVersionAndService(MServiceVersion version);
}
