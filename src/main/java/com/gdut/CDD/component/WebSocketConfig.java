package com.gdut.CDD.component;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author dengyq on 2018/8/13 下午4:58
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //服务端发送消息给客户端的域,多个用逗号隔开
        config.enableSimpleBroker("/topic", "/user");
        //定义websoket前缀
        config.setApplicationDestinationPrefixes("/app");
        //定义一对一推送的时候前缀
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/CDDWebsocket").withSockJS();
    }
}
