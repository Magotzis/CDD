package utils;

import model.Poker;

import java.util.Comparator;

/**
 * @author dengyq on 2018/7/24 下午5:17
 */
public class SortCompator implements Comparator<Poker> {

    @Override
    public int compare(Poker o1, Poker o2) {
        if (o1.getNumber() == o2.getNumber()) {
            return o1.getType() > o2.getType() ? -1 : 1;
        }
        if (o1.getNumber() == 3) return -1;
        if (o2.getNumber() == 3) return 1;
        if (o1.getNumber() == 2 && o2.getNumber() !=3 ) return -1;
        if (o2.getNumber() == 2 && o1.getNumber() !=3 ) return 1;
        if (o1.getNumber() == 1 && (o2.getNumber() != 3 || o2.getNumber() !=2)) return -1;
        if (o2.getNumber() == 1 && (o1.getNumber() != 3 || o1.getNumber() !=2)) return 1;
        return o1.getNumber() > o2.getNumber() ? -1 : 1;
    }
}
