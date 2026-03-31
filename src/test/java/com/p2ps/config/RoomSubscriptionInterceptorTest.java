package com.p2ps.config;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RoomSubscriptionInterceptorTest {

    private final RoomSubscriptionInterceptor interceptor = new RoomSubscriptionInterceptor();

    private Message<?> createMessage(StompCommand command, String destination) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(command);
        if (destination != null) {
            accessor.setDestination(destination);
        }
        return MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());
    }

    @Test
    void preSend_ValidSubscription() {
        Message<?> message = createMessage(StompCommand.SUBSCRIBE, "/topic/list/valid-ID-123");
        MessageChannel channel = mock(MessageChannel.class);

        Message<?> result = interceptor.preSend(message, channel);

        assertSame(message, result);
    }

    @Test
    void preSend_InvalidSubscription_ThrowsException() {
        Message<?> message = createMessage(StompCommand.SUBSCRIBE, "/topic/list/invalid_ID!");
        MessageChannel channel = mock(MessageChannel.class);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            interceptor.preSend(message, channel);
        });

        assertEquals("Invalid List ID format", exception.getMessage());
    }

    @Test
    void preSend_OtherCommand_Passes() {
        Message<?> message = createMessage(StompCommand.SEND, "/topic/list/invalid_ID!");
        MessageChannel channel = mock(MessageChannel.class);

        Message<?> result = interceptor.preSend(message, channel);

        assertSame(message, result);
    }

    @Test
    void preSend_NoDestination_Passes() {
        Message<?> message = createMessage(StompCommand.SUBSCRIBE, null);
        MessageChannel channel = mock(MessageChannel.class);

        Message<?> result = interceptor.preSend(message, channel);

        assertSame(message, result);
    }

    @Test
    void preSend_OtherPrefix_Passes() {
        Message<?> message = createMessage(StompCommand.SUBSCRIBE, "/topic/other/invalid_ID!");
        MessageChannel channel = mock(MessageChannel.class);

        Message<?> result = interceptor.preSend(message, channel);

        assertSame(message, result);
    }
}

