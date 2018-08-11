package service;

import enums.CardType;
import model.Poker;

import java.util.List;

/**
 * @author dengyq on 2018/7/26 下午7:22
 */
public interface CardLogicService {

    /**
     * 验证出牌是否正确且类型是什么
     * @param list 出的牌
     */
    CardType judgeCardType(List<Poker> list);

    boolean isCorrectAndBiggerThanLast(List<Poker> left, List<Poker> right);
}
