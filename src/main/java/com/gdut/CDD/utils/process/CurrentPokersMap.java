package com.gdut.CDD.utils.process;

import com.gdut.CDD.model.Poker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengyq on 2018/8/14 下午5:08
 */
public class CurrentPokersMap {

    private CurrentPokersMap() {
    }

    private static Map<String, List<Poker>> map = new HashMap<>();

    public static List<Poker> get(String name) {
        return map.get(name);
    }

    public static void set(String name, List<Poker> list) {
        map.put(name, list);
    }

    public static void clear() {
        map.clear();
    }

    public static String printRestCard() {
        StringBuilder content = new StringBuilder();
        map.forEach((k,v) -> content.append(k).append(":").append(v.size()).append("  "));
        return content.toString();
    }
}
