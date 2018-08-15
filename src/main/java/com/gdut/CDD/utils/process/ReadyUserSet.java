package com.gdut.CDD.utils.process;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author dengyq on 2018/8/14 下午4:50
 */
public class ReadyUserSet {

    private ReadyUserSet() {
    }

    private static Set<String> set = new TreeSet<>();

    public static int get() {
        return set.size();
    }

    public static int setAndGet(String name) {
        set.add(name);
        return set.size();
    }

    public static Set<String> getAll(){
        return set;
    }

    public static void reset() {
        set.clear();
    }

}
