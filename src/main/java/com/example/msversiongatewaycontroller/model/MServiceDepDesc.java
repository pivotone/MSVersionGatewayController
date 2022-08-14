package com.example.msversiongatewaycontroller.model;

import java.util.Map;

public class MServiceDepDesc {
    private String serviceId;
    private Map<String, Map<String, MServiceDependency>> dependencyMaps;
    private String name;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Map<String, Map<String, MServiceDependency>> getDependencyMaps() {
        return dependencyMaps;
    }

    public void setDependencyMaps(Map<String, Map<String, MServiceDependency>> dependencyMaps) {
        this.dependencyMaps = dependencyMaps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
