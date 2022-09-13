package com.example.msversiongatewaycontroller.serviceImpl;

import com.example.msversiongatewaycontroller.entity.ServiceVersionInterface;
import com.example.msversiongatewaycontroller.mapper.ServiceVersionInterfaceMapper;
import com.example.msversiongatewaycontroller.service.ServiceVersionInterfaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author pivot
 * @since 2022-09-12
 */
@Service
public class ServiceVersionInterfaceServiceImpl extends ServiceImpl<ServiceVersionInterfaceMapper, ServiceVersionInterface> implements ServiceVersionInterfaceService {

}
