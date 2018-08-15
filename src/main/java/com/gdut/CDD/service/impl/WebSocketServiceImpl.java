package com.gdut.CDD.service.impl;

import com.gdut.CDD.model.vo.SocketMessage;
import com.gdut.CDD.service.WebSocketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dengyq on 2018/8/14 上午9:51
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {


    @Resource
    private SimpMessagingTemplate template;

    /**
     * 广播
     * 发给所有在线用户
     */
    public void sendMsg(SocketMessage msg, String dest) {
        template.convertAndSend(dest, msg.getMessage());
    }

    /**
     * 发送给指定用户
     */
    public void send2Users(List<String> users, SocketMessage msg, String dest) {
        users.forEach(userName -> template.convertAndSendToUser(userName, dest, msg.getMessage()));
    }

    @Override
    public void send2User(String userName, Object msg, String dest) {
        template.convertAndSendToUser(userName, dest, msg);

    }
}
