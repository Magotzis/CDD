package com.gdut.CDD.utils.process;

import com.gdut.CDD.model.vo.Status;

import java.util.Comparator;

/**
 * @author dengyq on 2018/7/24 下午5:17
 */
public class StatusCompator implements Comparator<Status> {

    @Override
    public int compare(Status o1, Status o2) {
        if (o1.isFinish() && !o2.isFinish()) {
            return 1;
        }
        if (!o1.isFinish() && o2.isFinish()) {
            return -1;
        }
        return Integer.compare(o1.getCount(), o2.getCount());
    }
}
