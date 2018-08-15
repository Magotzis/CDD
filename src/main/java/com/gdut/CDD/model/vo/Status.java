package com.gdut.CDD.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dengyq on 2018/8/14 下午9:39
 */
@Data
@AllArgsConstructor
public class Status {

    private String name;

    private int count;

    private boolean isFinish;
}
