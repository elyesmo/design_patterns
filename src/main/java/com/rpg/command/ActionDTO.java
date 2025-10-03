package com.rpg.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Data Transfer Object representing an action for safe replay.
 * Stores action type and arguments without holding references to mutable objects.
 */
public class ActionDTO {
    private final String actionType;
    private final Map<String, Object> args;
    private final long timestamp;

    public ActionDTO(String actionType) {
        this.actionType = actionType;
        this.args = new HashMap<>();
        this.timestamp = System.currentTimeMillis();
    }

    public String getActionType() {
        return actionType;
    }

    public ActionDTO addArg(String key, Object value) {
        args.put(key, value);
        return this;
    }

    public Object getArg(String key) {
        return args.get(key);
    }

    public String getStringArg(String key) {
        Object value = args.get(key);
        return value != null ? value.toString() : null;
    }

    public Integer getIntArg(String key) {
        Object value = args.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(actionType).append(" {");
        args.forEach((key, value) -> sb.append(key).append("=").append(value).append(", "));
        if (!args.isEmpty()) {
            sb.setLength(sb.length() - 2); // Remove trailing ", "
        }
        sb.append("}");
        return sb.toString();
    }
}

