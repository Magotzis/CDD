package service.impl;

import enums.CardType;
import model.Poker;
import service.CardLogicService;

import java.util.List;

/**
 * @author dengyq on 2018/7/26 下午7:22
 */
public class CardLogicServiceImpl implements CardLogicService {


    @Override
    public int isCorrect(List<Poker> list) {
        if (list == null || list.size() <= 0) return CardType.NOT_CARD_TYPE.getCode();
        if (list.size() == 1) {
            return CardType.SINGLE.getCode();
        }
        if (list.size() == 2 && list.get(0).getNumber() == list.get(1).getNumber()) {
            return CardType.PAIR.getCode();
        }
        if (list.size() == 3) {
            if (list.get(0).getNumber() == list.get(1).getNumber() && list.get(0).getNumber() == list.get(2).getNumber()) {
                return CardType.TRIPLE.getCode();
            }
        }
        /*
         检查五张牌的情况
          */
        // 检查顺子

        return CardType.NOT_CARD_TYPE.getCode();
    }
}
