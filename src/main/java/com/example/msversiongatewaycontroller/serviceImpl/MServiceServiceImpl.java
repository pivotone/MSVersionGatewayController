package com.example.msversiongatewaycontroller.serviceImpl;

import com.example.msversiongatewaycontroller.entity.MService;
import com.example.msversiongatewaycontroller.mapper.MServiceMapper;
import com.example.msversiongatewaycontroller.service.MServiceService;
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
public class MServiceServiceImpl extends ServiceImpl<MServiceMapper, MService> implements MServiceService {

    @Resource
    MServiceMapper serviceMapper;

    @Override
    public MService selectByServiceName(MService service) {
        return serviceMapper.selectByServiceName(service.getmServiceName());
    }
}
