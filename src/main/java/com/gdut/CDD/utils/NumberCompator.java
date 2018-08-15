package com.gdut.CDD.utils;

import com.gdut.CDD.model.Poker;

import java.util.Comparator;

/**
 * @author dengyq on 2018/8/11 上午10:13
 */
public class NumberCompator implements Comparator<Poker> {
    @Override
    public int compare(Poker o1, Poker o2) {
        return Integer.compare(o1.getNumber(), o2.getNumber());
    }

}
