package com.example.michael.lab2;

public class ChatModel implements Comparable {
    // class to contain chat message information
    public String sender, body, userId;
    public long time;

    public ChatModel(String sender, String message, String userId){
        // constructor to assign values
        this.sender = sender;
        this.body = message;
        this.userId = userId;
        this.time = System.currentTimeMillis();
    }

    // compare times of objects
    @Override
    public int compareTo(Object object) {
        return (time < ((ChatModel) object).time)?1:0;
    }
}
