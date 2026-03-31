package com.p2ps.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {

    ADD("ADD"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    UNKNOWN("UNKNOWN");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ActionType fromValue(String value) {
        if (value == null) {
            return UNKNOWN;
        }
        for (ActionType action : ActionType.values()) {
            if (action.value.equalsIgnoreCase(value)) {
                return action;
            }
        }
        return UNKNOWN;
    }
}
