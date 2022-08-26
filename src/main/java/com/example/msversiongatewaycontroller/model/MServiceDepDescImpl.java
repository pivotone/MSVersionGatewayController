package com.example.msversiongatewaycontroller.model;

import com.example.msversiongatewaycontroller.entity.MServiceDepDesc;
import com.example.msversiongatewaycontroller.entity.MServiceDependency;

import java.util.Map;

public class MServiceDepDescImpl extends MServiceDepDesc {
    private Map<String, Map<String, MServiceDependency>> dependencyMaps;

    public Map<String, Map<String, MServiceDependency>> getDependencyMaps() {
        return dependencyMaps;
    }

    public void setDependencyMaps(Map<String, Map<String, MServiceDependency>> dependencyMaps) {
        this.dependencyMaps = dependencyMaps;
    }
}
