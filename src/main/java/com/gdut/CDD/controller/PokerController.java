package com.gdut.CDD.controller;

import com.gdut.CDD.component.RedisRemote;
import com.gdut.CDD.enums.CardType;
import com.gdut.CDD.model.Poker;
import com.gdut.CDD.model.vo.PokerMessage;
import com.gdut.CDD.model.vo.Result;
import com.gdut.CDD.model.vo.SocketMessage;
import com.gdut.CDD.model.vo.Status;
import com.gdut.CDD.service.CardLogicService;
import com.gdut.CDD.service.PokerService;
import com.gdut.CDD.service.WebSocketService;
import com.gdut.CDD.utils.SortCompator;
import com.gdut.CDD.utils.process.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengyq on 2018/8/14 上午9:08
 */
@Controller
public class PokerController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    private PokerService pokerService;

    @Resource
    private CardLogicService cardLogicService;

    @Resource
    private WebSocketService webSocketService;

    @Resource
    private RedisRemote redisRemote;

    @MessageMapping("/begin")
    @SendTo("/topic/getResponse")
    public SocketMessage begin(SocketMessage message) {
        String username = (String) message.getMessage();
        int count = ReadyUserSet.setAndGet(username);
        CurrentUser.add(new Status(username, count, false));
        if (count < 4) {
            webSocketService.sendMsg(new SocketMessage(String.format("当前人数：%d，请稍后", count), new Date().toString()), "/topic/getResponse");
            return new SocketMessage(String.format("当前人数：%d，请稍后", count), new Date().toString());
        }
        // 发牌
        List<List<Poker>> lists = pokerService.deal();
        int i = 0;
        // 清掉上盘的数据
        FullPokersMap.clear();
        CurrentPokersMap.clear();
        CurrentUser.reset();
        ResultList.reset();

        for (String name : ReadyUserSet.getAll()) {
            List<Poker> pokers = lists.get(i++);
            if (pokers.contains(new Poker(4, 1))) {
                CurrentUser.name = name;
                CurrentUser.replaceSit(name);
            }
            if (pokers.contains(new Poker(1, 4)) || pokers.contains(new Poker(2, 4))) {
                ResultList.results.put(name, new Result(1, 0));
            } else {
                ResultList.results.put(name, new Result(0, 0));
            }
            FullPokersMap.set(name, pokers);
            CurrentPokersMap.set(name, pokers);
            webSocketService.send2User(name, pokers, "/msg");
        }
        CurrentUser.next = CurrentUser.getNext(CurrentUser.name);
        return new SocketMessage(String.format("当前人数：4，游戏开始，%s出牌", CurrentUser.name), new Date().toString());
    }

    @MessageMapping("/bringCard")
    public void bringCard(PokerMessage message) {
        boolean isNext = CurrentUser.isNext(message.getName());
        boolean isMine = CurrentUser.name.equals(message.getName());
        if (!isNext && !isMine) {
            webSocketService.send2User(message.getName(), "还没轮到你,狗批", "/errorMsg");
            return;
        }
        // 提取牌
        List<Poker> pokerList = new ArrayList<>();
        for (String str : message.getPokers()) {
            String[] strs = str.split("-");
            pokerList.add(new Poker(Integer.valueOf(strs[1]), Integer.valueOf(strs[0])));
        }
        List<Poker> havePoker = CurrentPokersMap.get(message.getName());
        // 检查牌是否合法
        boolean isCheck = havePoker.containsAll(pokerList);
        if (!isCheck) {
            webSocketService.send2User(message.getName(), "出些你没有的牌", "/errorMsg");
            return;
        }
        CardType cardType = cardLogicService.judgeCardType(pokerList);
        if (cardType == CardType.NOT_CARD_TYPE) {
            webSocketService.send2User(message.getName(), "乱出牌啊狗批", "/errorMsg");
            return;
        }
        if (!isMine) {
            boolean isBigger = cardLogicService.isCorrectAndBiggerThanLast(pokerList, CurrentUser.curPoker);
            if (!isBigger) {
                webSocketService.send2User(message.getName(), "打不过对面啊狗批", "/errorMsg");
                return;
            }
        }
        correctCardLogic(pokerList, message.getName(), havePoker);
        // 判断结果
        boolean isContinue = ifGameOver();
        if (isContinue) {
            sendMessage(pokerList, message.getName());
        } else {
            sendResultMessage();
        }
    }

    private void sendResultMessage() {
        List<Result> resultList = new ArrayList<>();
        ResultList.results.forEach((k, v) -> resultList.add(v));
        List<Result> finishResults = resultList.stream().filter(result -> result.getResult() > 0).collect(Collectors.toList());
        List<Result> dizhuPlayer = resultList.stream().filter(result -> result.getRole() == 1).collect(Collectors.toList());
        if (finishResults.containsAll(dizhuPlayer)) {
            webSocketService.sendMsg(new SocketMessage("游戏结束，地主获胜", new Date().toString()),"/topic/result");
            System.out.println("游戏结束，地主获胜");
        } else {
            webSocketService.sendMsg(new SocketMessage("游戏结束，农民获胜", new Date().toString()),"/topic/result");
            System.out.println("游戏结束，农民获胜");
        }
    }

    private boolean ifGameOver() {
        List<Result> resultList = new ArrayList<>();
        ResultList.results.forEach((k, v) -> resultList.add(v));
        List<Result> finishResults = resultList.stream().filter(result -> result.getResult() > 0).collect(Collectors.toList());
        List<Result> dizhuPlayer = resultList.stream().filter(result -> result.getRole() == 1).collect(Collectors.toList());
        List<Result> noDizhuPlayer = resultList.stream().filter(result -> result.getRole() == 0).collect(Collectors.toList());
        return finishResults.containsAll(dizhuPlayer) || finishResults.containsAll(noDizhuPlayer);
    }

    @MessageMapping("/pass")
    public void pass(PokerMessage message) {
        if (!message.getName().equals(CurrentUser.next)) {
            webSocketService.send2User(message.getName(), "都不到你，跳什么跳", "/errorMsg");
        }
        Poker[] pokers = new Poker[CurrentUser.curPoker.size()];
        CurrentUser.curPoker.toArray(pokers);
        String content = printPoker(pokers);
        CurrentUser.count++;
        CurrentUser.next = CurrentUser.getNext(message.getName());
        webSocketService.sendMsg(new SocketMessage(String.format("%s跳过,轮到%s出牌,上一轮牌为:%s\n对手牌剩余情况:%s", message.getName(),
                CurrentUser.next, content, CurrentPokersMap.printRestCard()), new Date().toString()), "/topic/getResponse");
    }

    private void sendMessage(List<Poker> pokerList, String name) {
        Poker[] pokers = new Poker[pokerList.size()];
        pokerList.toArray(pokers);
        String content = printPoker(pokers);
        String restCard = CurrentPokersMap.printRestCard();
        webSocketService.send2User(name, CurrentPokersMap.get(name), "/msg");
        String next = CurrentUser.getNext(name);
        webSocketService.sendMsg(new SocketMessage(String.format("轮到%s出牌,上一轮牌为:%s\n对手牌剩余情况:%s", next, content, restCard),
                new Date().toString()), "/topic/getResponse");

    }

    private void correctCardLogic(List<Poker> pokerList, String name, List<Poker> havePoker) {
        CurrentUser.count++;
        CurrentUser.curPoker = new ArrayList<>(pokerList);
        havePoker = havePoker.stream().filter(poker -> !pokerList.contains(poker)).collect(Collectors.toList());
        CurrentPokersMap.set(name, havePoker);
        if (havePoker.size() <= 0) {
            Result result = ResultList.results.get(name);
            result.setResult(ResultList.num++);
            ResultList.results.put(name, result);
            CurrentUser.changeStatus(name);
        }
    }

    private static String printPoker(Poker[] pokers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Poker poker : pokers) {
            if (poker.getType() == 1) stringBuilder.append("♦️");
            if (poker.getType() == 2) stringBuilder.append("♣️");
            if (poker.getType() == 3) stringBuilder.append("♥️");
            if (poker.getType() == 4) stringBuilder.append("♠️️");
            if (poker.getNumber() == 11) stringBuilder.append("J");
            else if (poker.getNumber() == 12) stringBuilder.append("Q");
            else if (poker.getNumber() == 13) stringBuilder.append("K");
            else stringBuilder.append(poker.getNumber()).append(" ");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
