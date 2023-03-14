package com.example.msversiongatewaycontroller.common;

import java.util.*;

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

    public String mergePrefix(String paths) {
        paths = paths.replace(" ", "");
        String[] path = paths.split(",");
        Map<String, Boolean> map = new HashMap<>();
        for(String p : path) {
            int pos = p.lastIndexOf("}");
            if(!map.containsKey(p.substring(0, pos + 1))) {
                map.put(p.substring(0, pos + 1), true);
            }
        }
        String[] temp = new String[map.size()];
        int j = 0;
        for(String key : map.keySet()) {
            temp[j++] = key;
        }
        Arrays.sort(temp, (a, b) -> b.length() - a.length());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < temp.length; ++i) {
            if(i != temp.length - 1) {
                sb.append(temp[i]).append("/**").append(", ");
            } else {
                sb.append(temp[i]).append("/**");
            }
        }
        return sb.toString();
    }
}
