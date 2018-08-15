package com.gdut.CDD.utils.process;

import com.gdut.CDD.model.vo.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dengyq on 2018/8/14 下午6:25
 */
public class ResultList {

    public static int num = 1;

    public static Map<String, Result> results = new HashMap<>();

    public static void reset() {
        num = 1;
        results.clear();
    }

}
