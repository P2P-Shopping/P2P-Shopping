package com.p2ps.dto;

/**
 * Data Transfer Object representing a real-time modification to a shopping list.
 * Defines the standard structure for messages broadcasted to list-specific WebSocket rooms.
 */
public class ListUpdatePayload {

    private String action;
    private String itemId;
    private String content;

    /**
     * Default constructor required for JSON deserialization by Jackson.
     */
    public ListUpdatePayload() {}

    /**
     * Gets the action type (e.g., "ADD", "DELETE", "UPDATE").
     * @return the action string
     */
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the unique identifier of the modified item.
     * @return the item ID
     */
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets the text content or value of the item.
     * @return the item content
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
