package com.example.msversiongatewaycontroller.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MServiceVersion {
    private int majorVersionNumber;
    private int minorVersionNumber;
    private int patchVersionNumber;
    private static final Logger logger = LogManager.getLogger(MServiceVersion.class);

    public int getMajorVersionNumber() {
        return majorVersionNumber;
    }

    public void setMajorVersionNumber(int majorVersionNumber) {
        this.majorVersionNumber = majorVersionNumber;
    }

    public int getMinorVersionNumber() {
        return minorVersionNumber;
    }

    public void setMinorVersionNumber(int minorVersionNumber) {
        this.minorVersionNumber = minorVersionNumber;
    }

    public int getPatchVersionNumber() {
        return patchVersionNumber;
    }

    public void setPatchVersionNumber(int patchVersionNumber) {
        this.patchVersionNumber = patchVersionNumber;
    }

    public MServiceVersion(int majorVersionNumber, int minorVersionNumber, int patchVersionNumber) {
        this.majorVersionNumber = majorVersionNumber;
        this.minorVersionNumber = minorVersionNumber;
        this.patchVersionNumber = patchVersionNumber;
    }

    public static MServiceVersion fromString(String versionString) {
        String[] versionArray =versionString.split("\\.");
        if(versionArray.length != 3) {
            logger.info("version is not legal" + versionString);
            throw new RuntimeException("version is not legal" + versionString);
        }

        MServiceVersion version = null;
        try {
            version = new MServiceVersion(
                    Integer.parseInt(versionArray[0]),
                    Integer.parseInt(versionArray[1]),
                    Integer.parseInt(versionArray[2]));
        } catch (Exception e) {
            logger.info(e);
        }

        return version;
    }

}
