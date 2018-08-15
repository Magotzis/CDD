package com.gdut.CDD.service.impl;

import com.gdut.CDD.model.Poker;
import com.gdut.CDD.service.PokerService;
import com.gdut.CDD.utils.SortCompator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author dengyq on 2018/8/14 上午9:11
 */
@Service
public class PokerServiceImpl implements PokerService {

    @Override
    public List<List<Poker>> deal() {
        List<List<Poker>> pokerLists = new ArrayList<>();
        List<Poker> library = new ArrayList<>(52);
        for (int i = 0; i < 13; i++) {
            library.add(new Poker(i + 1, 1));
            library.add(new Poker(i + 1, 2));
            library.add(new Poker(i + 1, 3));
            library.add(new Poker(i + 1, 4));
        }
        Collections.shuffle(library);
        Poker[] a = new Poker[13];
        Poker[] b = new Poker[13];
        Poker[] c = new Poker[13];
        Poker[] d = new Poker[13];
        for (int i = 0; i < 13; i++) {
            a[i] = library.get(i);
        }
        for (int j = 0, i = 13; i < 26; j++, i++) {
            b[j] = library.get(i);
        }
        for (int j = 0, i = 26; i < 39; j++, i++) {
            c[j] = library.get(i);
        }
        for (int j = 0, i = 39; i < 52; j++, i++) {
            d[j] = library.get(i);
        }
        Arrays.sort(a, new SortCompator());
        Arrays.sort(b, new SortCompator());
        Arrays.sort(c, new SortCompator());
        Arrays.sort(d, new SortCompator());
        pokerLists.add(Arrays.asList(a));
        pokerLists.add(Arrays.asList(b));
        pokerLists.add(Arrays.asList(c));
        pokerLists.add(Arrays.asList(d));
        return pokerLists;
    }
}
