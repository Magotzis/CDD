package com.gdut.CDD.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author dengyq on 2018/8/14 下午6:06
 */
@Data
public class PokerMessage {

    private String name;

    private List<String> pokers;
}
