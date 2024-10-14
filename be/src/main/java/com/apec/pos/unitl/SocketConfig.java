package com.apec.pos.unitl;

import com.apec.pos.entity.AccountEntity;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;


@EnableWebSocketMessageBroker
@Configuration
public class SocketConfig implements WebSocketMessageBrokerConfigurer {


    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @CrossOrigin
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-food")
                .setAllowedOrigins(
                        "http://127.0.0.1:5175",
                        "http://localhost:5175",
                        "http://14.225.206.173:8082",
                        "http://13.212.13.111:5175")
//                        "http://54.254.206.236:5174")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    List<String> authorization = accessor.getNativeHeader("Authorization");
                    String accessToken = authorization.get(0).substring(7);
                    System.out.println(accessToken);
                    AccountEntity accountEntity = accountRepository.findByUsername(jwtService.getUsernameFromToken(accessToken));
                    if (jwtService.validateToken(accessToken, accountEntity)) {
                        return message;
                    }
                    throw new MyCustomException("tài khoản không hợp lệ");
                }
                return message;
            }

        });
    }

    private class MyCustomException extends RuntimeException {
        public MyCustomException(String message) {
            super(message);
        }
    }


}
