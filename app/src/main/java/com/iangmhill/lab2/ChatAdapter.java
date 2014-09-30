package com.iangmhill.lab2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends ArrayAdapter {
    private List<ChatModel> chats = new ArrayList<ChatModel>();
    private int resource;
    private MyActivity context;

    public ChatAdapter(MyActivity context, List<ChatModel> chats, int resource) {
        super(context, R.layout.chat_item);
        // make context accessible from outside adapter (k?)
        this.context = context;
        this.resource = resource;

        addChats(chats);
    }

    private class ChatHolder {
        // maintains view information
        TextView name, body, time;
        ImageView picture;
        View background;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convertView is child
        ChatHolder chatHolder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null) { // this child is new // has not been rendered
            // inflater takes id of chat item and parent view
            convertView = inflater.inflate(resource, parent, false);

            // holder keeps all found views from last timestamp (we can update the views directly without re-finding them)
            chatHolder = new ChatHolder();

            // find elements in chat item, cast to views
            chatHolder.name = (TextView) convertView.findViewById(R.id.chat_item_name);
            chatHolder.body = (TextView) convertView.findViewById(R.id.chat_item_msg);
            chatHolder.time = (TextView) convertView.findViewById(R.id.chat_item_time);
            chatHolder.background = convertView.findViewById(R.id.chat_item_img);
            chatHolder.picture = (ImageView) convertView.findViewById(R.id.chat_item_pic);
            convertView.setTag(chatHolder);
        } else {
            chatHolder = (ChatHolder) convertView.getTag();
        }

        fillViews(chatHolder, chats.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return this.chats.size();
    }

    private void fillViews(ChatHolder holder, ChatModel chat) {
        holder.name.setText(chat.name);
        holder.body.setText(chat.message);
        Log.e("TIMESTAMP",chat.timestamp);
        holder.time.setText(formatTime(Long.parseLong(chat.timestamp)));
    }

    private String formatTime(long time) {
        if (DateUtils.isToday(time)) {
            return new SimpleDateFormat("E, hh:mm:ss a").format(new Date(time));
        }
        return new SimpleDateFormat("MM/DD, hh:mm:ss a").format(new Date(time));
    }

    private Drawable getProfileDrawable(String id) {
        return null;
    }

    public void addChats(List<ChatModel> newChats) {
        this.chats.addAll(newChats);
        notifyDataSetChanged();
    }

    public void addChat(ChatModel chat) {
        this.chats.add(chat);
        notifyDataSetChanged();
    }

    public ChatModel getChat(int index) {
        if(index + 1 > this.chats.size() || index < 0) {
            return null;
        } else {
            return this.chats.get(index);
        }
    }

    public String getChatMessage(int index){
        return this.chats.get(index).message;
    }

    public void deleteChat(ChatModel chat){
        this.chats.remove(chat);
        context.db.deleteMessageById(chat.timestamp);
        notifyDataSetChanged();
    }

    public void updateChatMessage(int index, String newMessage) {
        ChatModel chat = getChat(index);
        chat.message = newMessage;
        context.db.updateChatByTimestamp(chat, chat.timestamp);
        notifyDataSetChanged();
    }
}