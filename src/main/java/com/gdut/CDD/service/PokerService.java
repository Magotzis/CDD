package com.gdut.CDD.service;

import com.gdut.CDD.model.Poker;

import java.util.List;

/**
 * @author dengyq on 2018/8/14 上午9:11
 */
public interface PokerService {

    List<List<Poker>> deal();
}
