package com.example.msversiongatewaycontroller.model;

import java.util.Map;

public class MService {
    private MServiceVersion version;
    private String MServiceName;
    private String Description;
    private Map<String, MServiceInterface> serviceInterfaceMap;
    private MServiceDepDesc mServiceDepDesc;
}
