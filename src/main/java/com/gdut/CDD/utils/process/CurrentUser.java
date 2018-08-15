package com.gdut.CDD.utils.process;

import com.gdut.CDD.model.Poker;
import com.gdut.CDD.model.vo.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dengyq on 2018/8/14 下午5:36
 */
public class CurrentUser {

    public static int count;

    public static String name;

    public static String next;

    private static List<Status> statusList = new ArrayList<>();

    public static List<Poker> curPoker = new ArrayList<>();

    public static boolean isNext(String cur) {
        Status nameStatus = null;
        for (Status status : statusList) {
            if (cur.equals(status.getName())) {
                nameStatus = status;
                break;
            }
        }
        assert nameStatus != null;
        return (count + 1) % nameStatus.getCount() == 0;
    }

    public static String getNext(String name) {
        Status nameStatus = null;
        for (Status status : statusList) {
            if (name.equals(status.getName())) {
                nameStatus = status;
                break;
            }
        }
        assert nameStatus != null;
        int next = nameStatus.getCount() + 1;
        if (next == 5) {
            next = 1;
        }
        String username = "";
        for (Status status : statusList) {
            if (next == status.getCount()) {
                username = status.getName();
                break;
            }
        }
        return username;
    }

    public static void reset() {
        count = 0;
        name = "";
        curPoker.clear();
        List<Status> list = new ArrayList<>();
        statusList.forEach(s -> list.add(new Status(s.getName(), s.getCount(), false)));
        statusList = list;
    }

    public static void add(Status status) {
        statusList.add(status);
    }

    public static void changeStatus(String name) {
        Status nameStatus = null;
        for (Status status : statusList) {
            if (name.equals(status.getName())) {
                nameStatus = status;
                break;
            }
        }
        Status replace = new Status(name, nameStatus.getCount(), true);
        statusList.remove(nameStatus);
        statusList.add(replace);
    }

    public static void replaceSit(String name) {
        Status nameStatus = null;
        for (Status status : statusList) {
            if (name.equals(status.getName())) {
                nameStatus = status;
                break;
            }
        }
        Status first = null;
        for (Status status : statusList) {
            if (status.getCount() == 1) {
                first = status;
                break;
            }
        }
        int index = nameStatus.getCount();
        statusList.remove(nameStatus);
        statusList.remove(first);
        statusList.add(new Status(name, 1, false));
        statusList.add(new Status(first.getName(), index, false));
    }
}
