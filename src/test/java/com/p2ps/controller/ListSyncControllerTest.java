package com.p2ps.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.p2ps.dto.ListUpdatePayload;

class ListSyncControllerTest {

    @Test
    void handleListUpdate() {
        ListSyncController controller = new ListSyncController();
        ListUpdatePayload payload = new ListUpdatePayload();
        payload.setAction("UPDATE");

        ListUpdatePayload result = controller.handleListUpdate("list-1", payload);

        assertSame(payload, result);
    }
}

