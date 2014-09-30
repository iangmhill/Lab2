package com.iangmhill.lab2;

public class ChatModel implements Comparable {
    // class to contain chat message information
    public String name, message, timestamp;

    public ChatModel(String name, String message){
        // constructor to assign values
        this.name = name;
        this.message = message;
        this.timestamp = Long.toString(System.currentTimeMillis());
    }
    public ChatModel(String name, String message,String timestamp){
        // constructor to assign values
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

    // compare times of objects
    @Override
    public int compareTo(Object object) {
        return (Long.getLong(timestamp) < Long.getLong(((ChatModel) object).timestamp))?1:0;
    }
}
