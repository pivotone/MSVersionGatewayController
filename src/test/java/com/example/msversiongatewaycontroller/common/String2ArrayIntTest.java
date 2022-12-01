package com.example.msversiongatewaycontroller.common;

import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class String2ArrayIntTest {

    @Test
    void testStringVersionToIntArray() {
        VersionStringOp string2ArrayInt = new VersionStringOp();
        String version = "0.17.1";
        int[] versions = string2ArrayInt.stringVersionToIntArray(version);
        System.out.println("major version is " + versions[0] + ", minor version is " + versions[1] + ", patch version is " + versions[2]);
    }

    @Test
    void testCompareVersion() {
        VersionStringOp stringOp = new VersionStringOp();
        String version1 = "0.17.1";
        String version2 = "0.2.1";
        System.out.println("version2 < version1 is " + stringOp.compareVersion(version2, version1));
    }
}