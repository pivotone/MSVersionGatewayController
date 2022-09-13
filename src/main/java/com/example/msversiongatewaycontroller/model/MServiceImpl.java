package com.example.msversiongatewaycontroller.model;

import com.example.msversiongatewaycontroller.entity.MService;
import com.example.msversiongatewaycontroller.entity.MServiceInterface;
import com.example.msversiongatewaycontroller.entity.MServiceVersion;

import java.util.Map;

public class MServiceImpl extends MService {
    private Map<String, MServiceInterface> serviceInterfaceMap;
    private MServiceVersion version;

    public Map<String, MServiceInterface> getServiceInterfaceMap() {
        return serviceInterfaceMap;
    }

    public void setServiceInterfaceMap(Map<String, MServiceInterface> serviceInterfaceMap) {
        this.serviceInterfaceMap = serviceInterfaceMap;
    }

    public MServiceVersion getVersion() {
        return version;
    }

    public void setVersion(MServiceVersion version) {
        this.version = version;
    }
}
