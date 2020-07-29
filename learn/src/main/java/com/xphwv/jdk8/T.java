package com.xphwv.jdk8;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xupan on 2019-03-27
 */
public class T {
    public static void main(String[] args) {

        Map<Integer, Integer> optionalMap = new HashMap();
        System.out.println(optionalMap.get(1));
        System.out.println(Collections.EMPTY_MAP.containsKey(1));
    }
}
