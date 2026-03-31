package com.p2ps.config;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WebSocketConfigTest {

    @Test
    void registerStompEndpoints() {
        RoomSubscriptionInterceptor interceptor = mock(RoomSubscriptionInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);

        StompEndpointRegistry registry = mock(StompEndpointRegistry.class);
        StompWebSocketEndpointRegistration reg = mock(StompWebSocketEndpointRegistration.class);

        when(registry.addEndpoint(anyString())).thenReturn(reg);
        when(reg.setAllowedOriginPatterns(any())).thenReturn(reg);

        config.registerStompEndpoints(registry);

        verify(registry).addEndpoint("/ws");
        verify(reg).setAllowedOriginPatterns((String[]) null); // value is not injected in pure unit test, so null
        verify(reg).withSockJS();
    }

    @Test
    void configureMessageBroker() {
        RoomSubscriptionInterceptor interceptor = mock(RoomSubscriptionInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);

        MessageBrokerRegistry registry = mock(MessageBrokerRegistry.class);

        config.configureMessageBroker(registry);

        verify(registry).enableSimpleBroker("/topic");
        verify(registry).setApplicationDestinationPrefixes("/app");
    }

    @Test
    void configureClientInboundChannel() {
        RoomSubscriptionInterceptor interceptor = mock(RoomSubscriptionInterceptor.class);
        WebSocketConfig config = new WebSocketConfig(interceptor);

        ChannelRegistration registration = mock(ChannelRegistration.class);

        config.configureClientInboundChannel(registration);

        verify(registration).interceptors(interceptor);
    }
}

