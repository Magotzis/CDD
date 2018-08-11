package service.impl;

import enums.CardType;
import exception.NotCardTypeException;
import exception.WrongCradLogicException;
import model.Poker;
import service.CardLogicService;
import utils.NumberCompator;
import utils.SortCompator;

import java.util.List;

import static enums.CardType.*;

/**
 * @author dengyq on 2018/7/26 下午7:22
 */
public class CardLogicServiceImpl implements CardLogicService {


    @Override
    public CardType judgeCardType(List<Poker> list) {
        if (list == null || list.size() <= 0) return NOT_CARD_TYPE;
        //单张
        if (list.size() == 1) {
            return SINGLE;
        }
        // 对子
        if (list.size() == 2 && list.get(0).getNumber() == list.get(1).getNumber()) {
            return PAIR;
        }
        // 三张
        if (list.size() == 3) {
            Poker finalPoker = list.get(0);
            boolean isTrue = list.stream().allMatch(p -> p.getNumber() == finalPoker.getNumber());
            if (isTrue) {
                return TRIPLE;
            }
        }
        // 检查顺子
        boolean isFlush = checkFlush(list);
        if (isFlush) {
            Poker finalPoker = list.get(0);
            boolean isSflush = list.stream().allMatch(p -> p.getType() == finalPoker.getType());
            if (isSflush) {
                return STRAIGHT_FLUSH;
            }
            return FLUSH;
        }
        // 检查三带二，四带一
        return checkFiveCard(list);
    }

    @Override
    public boolean isCorrectAndBiggerThanLast(List<Poker> left, List<Poker> right) {
        if (left.size() != right.size()) {
            throw new WrongCradLogicException();
        }
        CardType leftCardType = judgeCardType(left);
        CardType rightCardType = judgeCardType(right);
        if (leftCardType == NOT_CARD_TYPE || rightCardType == NOT_CARD_TYPE) {
            throw new NotCardTypeException();
        }
        left.sort(new SortCompator());
        right.sort(new SortCompator());
        // 上一手出同花顺情况
        if (rightCardType == STRAIGHT_FLUSH) {
            if (leftCardType == STRAIGHT_FLUSH) {
                // 同花顺比最大牌的大小
                return left.get(left.size() - 1).compareTo(right.get(right.size() - 1)) > 0;
            }
            return false;
        }
        // 上一手出四带一情况
        if (rightCardType == FOURTH_BELT_ONE) {
            if (leftCardType == STRAIGHT_FLUSH) {
                // 同花顺压死
                return true;
            }
            if (leftCardType == FOURTH_BELT_ONE) {
                // 四带一比大小
                return left.get(left.size() - 1).compareTo(right.get(right.size() - 1)) > 0;
            }
            if (leftCardType.inRanger(FLUSH, THREE_BELT_TWO)) {
                // 顺子，三带二
                return false;
            }

        }
        // 上一手出三带二情况
        if (rightCardType == THREE_BELT_TWO) {
            if (leftCardType.inRanger(FOURTH_BELT_ONE, STRAIGHT_FLUSH)) {
                // 四带一，同花顺压死
                return true;
            }
            if (leftCardType == THREE_BELT_TWO) {
                // 三带二比大小
                return left.get(left.size() - 1).compareTo(right.get(right.size() - 1)) > 0;
            }
            if (leftCardType == FLUSH) {
                // 顺子
                return false;
            }
        }
        // 上一手出顺子情况
        if (rightCardType == FLUSH) {
            if (leftCardType.inRanger(THREE_BELT_TWO, STRAIGHT_FLUSH)) {
                // 三带二，四带一，同花顺压死
                return true;
            }
            if (leftCardType == FLUSH) {
                // 顺子比大小
                return left.get(left.size() - 1).compareTo(right.get(right.size() - 1)) > 0;
            }
        }
        // 剩下的直接比最大牌大小就行
        return left.get(left.size() - 1).compareTo(right.get(right.size() - 1)) > 0;
    }

    private CardType checkFiveCard(List<Poker> list) {
        if (list.size() != 5) {
            return NOT_CARD_TYPE;
        }
        int one = 1, another = 0;
        Poker poker = list.get(0), otherPoker = null;
        for (int i = 1; i < list.size(); i++) {
            Poker temp = list.get(i);
            if (temp.getNumber() == poker.getNumber()) {
                one++;
            } else {
                if (otherPoker == null) {
                    otherPoker = temp;
                    another++;
                } else if (temp.getNumber() == otherPoker.getNumber()) {
                    another++;
                } else {
                    return NOT_CARD_TYPE;
                }
            }
        }
        if ((one == 3 && another == 2) || (one == 2 && another == 3)) {
            return THREE_BELT_TWO;
        }
        if ((one == 4 && another == 1) || (one == 1 && another == 4)) {
            return FOURTH_BELT_ONE;
        }
        return NOT_CARD_TYPE;
    }

    private boolean checkFlush(List<Poker> list) {
        if (list.size() != 5) {
            return false;
        }
        list.sort(new SortCompator());
        Poker poker = list.get(0);
        // 处理10JQKA特殊情况
        if (poker.getNumber() == 10) {
            for (int i = 1; i < list.size() - 1; i++) {
                Poker temp = list.get(i);
                if (temp.getNumber() == poker.getNumber() + 1) {
                    poker = temp;
                } else {
                    return false;
                }
            }
            return list.get(list.size() - 1).getNumber() == 1;
        }
        list.sort(new NumberCompator());
        poker = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Poker temp = list.get(i);
            if (temp.getNumber() == poker.getNumber() + 1) {
                poker = temp;
            } else {
                return false;
            }
        }
        return true;
    }
}
