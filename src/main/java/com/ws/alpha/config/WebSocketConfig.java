package com.ws.alpha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author winson
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        //表示客户端订阅地址的前缀信息，即客户端接收服务端地址信息的前缀信息
        config.enableSimpleBroker("/wechat");
        //客户端给服务端地址的前缀信息
        config.setApplicationDestinationPrefixes("/wechat");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //添加一个服务端点，接收客户端的连接
        stompEndpointRegistry.addEndpoint("/ws-guide-websocket").withSockJS();
    }
}
