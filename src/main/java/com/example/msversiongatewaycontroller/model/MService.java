package com.example.msversiongatewaycontroller.model;

import java.util.Map;

public class MService {
    private MServiceVersion version;
    private String mServiceName;
    private String description;
    private Map<String, MServiceInterface> serviceInterfaceMap;
    private MServiceDepDesc mServiceDepDesc;

    public MServiceVersion getVersion() {
        return version;
    }

    public void setVersion(MServiceVersion version) {
        this.version = version;
    }

    public String getMServiceName() {
        return mServiceName;
    }

    public void setMServiceName(String MServiceName) {
        this.mServiceName = MServiceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, MServiceInterface> getServiceInterfaceMap() {
        return serviceInterfaceMap;
    }

    public void setServiceInterfaceMap(Map<String, MServiceInterface> serviceInterfaceMap) {
        this.serviceInterfaceMap = serviceInterfaceMap;
    }

    public MServiceDepDesc getmServiceDepDesc() {
        return mServiceDepDesc;
    }

    public void setmServiceDepDesc(MServiceDepDesc mServiceDepDesc) {
        this.mServiceDepDesc = mServiceDepDesc;
    }
}
