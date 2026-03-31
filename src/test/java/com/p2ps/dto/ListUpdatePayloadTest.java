package com.p2ps.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ListUpdatePayloadTest {

    @Test
    void testGettersAndSetters() {
        ListUpdatePayload payload = new ListUpdatePayload();

        payload.setAction("ADD");
        assertEquals("ADD", payload.getAction());

        payload.setItemId("item-123");
        assertEquals("item-123", payload.getItemId());

        payload.setContent("Apples");
        assertEquals("Apples", payload.getContent());
    }
}

