package com.gdut.CDD.service;


import com.gdut.CDD.model.vo.SocketMessage;

import java.util.List;

/**
 * @author dengyq on 2018/8/14 上午9:51
 */
public interface WebSocketService {

    void sendMsg(SocketMessage msg, String dest);

    void send2Users(List<String> users, SocketMessage msg, String dest);

    void send2User(String users, Object msg, String dest);

}
