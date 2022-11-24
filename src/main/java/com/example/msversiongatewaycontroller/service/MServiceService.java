package com.example.msversiongatewaycontroller.service;

import com.example.msversiongatewaycontroller.entity.MService;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
public interface MServiceService extends IService<MService> {

    MService selectByServiceName(MService service);
}
