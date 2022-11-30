package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.MServiceInterface;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
public interface MServiceInterfaceService extends IService<MServiceInterface> {
    MServiceInterface selectInterfaceByMethodAndApi(MServiceInterface serviceInterface);


    boolean saveInterface(MServiceInterface entity);
}
