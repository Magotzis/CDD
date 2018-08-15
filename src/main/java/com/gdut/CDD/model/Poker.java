package com.gdut.CDD.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dengyq on 2018/7/24 下午4:53
 */
@Data
@AllArgsConstructor
public class Poker implements Comparable{

    private int number;

    private int type;

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return number == ((Poker) obj).number && type == ((Poker) obj).type;
    }

    @Override
    public int compareTo(Object obj) {
        Poker poker = (Poker) obj;
        if (number == poker.getNumber()) {
            return type > poker.getType() ? 1 : -1;
        }
        if (number == 3) return 1;
        if (poker.getNumber() == 3) return -1;
        if (number == 2 && poker.getNumber() !=3 ) return 1;
        if (poker.getNumber() == 2 && number !=3 ) return -1;
        if (number == 1 && (poker.getNumber() != 3 || poker.getNumber() !=2)) return 1;
        if (poker.getNumber() == 1 && (number != 3 || number !=2)) return -1;
        return number > poker.getNumber() ? 1 : -1;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
