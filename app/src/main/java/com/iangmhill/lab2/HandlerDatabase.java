package com.iangmhill.lab2;

/**
 * Created by ihill on 9/21/2014.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HandlerDatabase {
    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.MESSAGE_TIME,
            ModelDatabase.MESSAGE_SENDER,
            ModelDatabase.MESSAGE_BODY,
            ModelDatabase.MESSAGE_USERID
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addMessageToDatabase(ChatModel message){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.MESSAGE_TIME, Long.toString(message.time));
        values.put(ModelDatabase.MESSAGE_SENDER, message.sender);
        values.put(ModelDatabase.MESSAGE_BODY, message.body);
        values.put(ModelDatabase.MESSAGE_USERID, message.userId);
        database.insertWithOnConflict(ModelDatabase.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    public void updateMessage(ChatModel message){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.MESSAGE_TIME, Long.toString(message.time));
        values.put(ModelDatabase.MESSAGE_SENDER, message.sender);
        values.put(ModelDatabase.MESSAGE_BODY, message.body);
        values.put(ModelDatabase.MESSAGE_USERID, message.userId);
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.MESSAGE_TIME + " like '%" + Long.toString(message.time) + "%'", null);
    }

    /**
     * Get
     */
    public ArrayList<ChatModel> getAllMessages(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, null, null, null, null, null));
    }
    public ChatModel getMessageById(Long time){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.MESSAGE_TIME + " like '%" + Long.toString(time) + "%'",
                null, null, null, null
        )).get(0);
    }

    /**
     * Delete
     */
    public void deleteMessageById(Long time){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.MESSAGE_TIME + " like '%" + Long.toString(time) + "%'",
                null
        );
    }

    /**
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Messages
    private ArrayList<ChatModel> sweepCursor(Cursor cursor){
        ArrayList<ChatModel> messages = new ArrayList<ChatModel>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the message
            ChatModel message = new ChatModel(
                    Long.parseLong(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            //Add the message
            messages.add(message);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return messages;
    }

    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}
