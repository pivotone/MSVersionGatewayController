package com.example.msversiongatewaycontroller.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author pivot
 */
public class RemoveDuplication {

    public String removedResult(String duplication) {
        duplication = duplication.replace(" ", "");
        String[] strs = duplication.split(",");
        Set<String> set = new HashSet<>(Arrays.asList(strs).subList(0, strs.length - 1));
        StringBuilder sb = new StringBuilder();
        set.forEach(str -> {
            sb.append(str).append(", ");
        });
        sb.append(strs[strs.length - 1]);
        return sb.toString();
    }
}
