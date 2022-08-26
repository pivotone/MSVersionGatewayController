package com.example.msversiongatewaycontroller.model;

import com.example.msversiongatewaycontroller.entity.MServiceDependency;
import com.example.msversiongatewaycontroller.entity.MServiceVersion;

import java.util.Set;

public class MServiceDependencyImpl extends MServiceDependency {
    private Set<MServiceVersion> versionSet;

    public Set<MServiceVersion> getVersionSet() {
        return versionSet;
    }

    public void setVersionSet(Set<MServiceVersion> versionSet) {
        this.versionSet = versionSet;
    }


}
