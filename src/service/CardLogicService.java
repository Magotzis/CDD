package service;

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
    int isCorrect(List<Poker> list);
}
