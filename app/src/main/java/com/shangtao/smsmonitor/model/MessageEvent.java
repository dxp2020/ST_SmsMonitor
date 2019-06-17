package com.shangtao.smsmonitor.model;

import java.util.HashMap;
import java.util.Map;

public class MessageEvent {
    private EventType eventType;
    private Map<String,String> data = new HashMap<>();

    public MessageEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public MessageEvent(EventType eventType, Map<String, String> data) {
        this.eventType = eventType;
        this.data = data;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
