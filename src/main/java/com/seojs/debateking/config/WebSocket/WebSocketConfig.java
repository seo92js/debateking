package com.seojs.debateking.config.WebSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //메세지 송수신
    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry){
        //sub로 보내면 이곳을 한번 거쳐서 프론트에 데이터 전달?
        messageBrokerRegistry.enableSimpleBroker("/sub");
        //pub로 데이터를 받으면 이곳을 한번 거쳐서 URI 만 MessageMapping 에 매핑 된다.
        //pub/chat/message 라면 pub을 제외하고 /chat/message 를 @MessageMapping에 매핑
        messageBrokerRegistry.setApplicationDestinationPrefixes("/pub");
    }

    //서버와 연결
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry){
        stompEndpointRegistry.addEndpoint("/chattings")
                .setAllowedOriginPatterns("*").withSockJS();
    }
}
