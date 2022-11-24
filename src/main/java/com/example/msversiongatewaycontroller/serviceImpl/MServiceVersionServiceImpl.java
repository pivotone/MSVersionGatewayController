package com.example.msversiongatewaycontroller.serviceImpl;

import com.example.msversiongatewaycontroller.entity.MServiceVersion;
import com.example.msversiongatewaycontroller.mapper.MServiceVersionMapper;
import com.example.msversiongatewaycontroller.service.MServiceVersionService;
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
public class MServiceVersionServiceImpl extends ServiceImpl<MServiceVersionMapper, MServiceVersion> implements MServiceVersionService {
    @Resource
    MServiceVersionMapper versionMapper;

    @Override
    public MServiceVersion selectByVersionAndService(MServiceVersion version) {
        return versionMapper.selectByVersionAndService(version);
    }
}
