package com.example.msversiongatewaycontroller.common;

import org.jetbrains.annotations.NotNull;

public class VersionStringOp {
    public int[] stringVersionToIntArray(@NotNull String version) {
        String[] versions = version.split("\\.");
        int[] arrays = new int[3];
        arrays[0] = Integer.parseInt(versions[0]);
        arrays[1] = Integer.parseInt(versions[1]);
        arrays[2] = Integer.parseInt(versions[2]);

        return arrays;
    }

    public boolean compareVersion(String version1, String version2) {
        int[] versionArray1 = stringVersionToIntArray(version1);
        int[] versionArray2 = stringVersionToIntArray(version2);
        return versionArray1[0] <= versionArray2[0]
                && versionArray1[1] <= versionArray2[1]
                && versionArray1[2] <= versionArray2[2];
    }
}
