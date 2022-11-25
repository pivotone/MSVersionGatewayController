package com.example.msversiongatewaycontroller.mapper;

import com.example.msversiongatewaycontroller.entity.MServiceInterface;
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
public interface MServiceInterfaceMapper extends BaseMapper<MServiceInterface> {
    MServiceInterface selectInterfaceByMethodAndApi(MServiceInterface serviceInterface);
}
