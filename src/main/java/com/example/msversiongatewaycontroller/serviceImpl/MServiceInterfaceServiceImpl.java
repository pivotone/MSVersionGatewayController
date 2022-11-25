package com.example.msversiongatewaycontroller.serviceImpl;

import com.example.msversiongatewaycontroller.entity.MServiceInterface;
import com.example.msversiongatewaycontroller.mapper.MServiceInterfaceMapper;
import com.example.msversiongatewaycontroller.service.MServiceInterfaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@Service
public class MServiceInterfaceServiceImpl extends ServiceImpl<MServiceInterfaceMapper, MServiceInterface> implements MServiceInterfaceService {

    @Resource
    MServiceInterfaceMapper interfaceMapper;
    @Override
    public MServiceInterface selectInterfaceByMethodAndApi(MServiceInterface serviceInterface) {
        return interfaceMapper.selectInterfaceByMethodAndApi(serviceInterface);
    }
}
