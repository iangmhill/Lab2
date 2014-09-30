package com.iangmhill.lab2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyActivity extends Activity {
    ChatAdapter chatAdapter;
    public static String username = "default";
    public HandlerDatabase db;
    public static Firebase myFirebaseRef = new Firebase("https://earlgrey.firebaseio.com/chatroom");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my); // inflater (wat?)

        /**
         *  Database
         */
        db = new HandlerDatabase(this);
        db.open();
        Query postsQuery = myFirebaseRef.limit(10);
        postsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                HashMap<String,String> postInfo = (HashMap) snapshot.getValue();
                chatAdapter.addChat(new ChatModel(postInfo.get("name"),postInfo.get("message"),postInfo.get("timestamp")));
                chatAdapter.notifyDataSetChanged();
            }
            public void onChildRemoved(DataSnapshot snapshot){

            }
            public void onChildChanged(DataSnapshot dataSnapshot, java.lang.String s){

            }
            public void onChildMoved(DataSnapshot dataSnapshot, java.lang.String s){

            }

            public void onCancelled(FirebaseError firebaseError){

            }

        });

        if (username.equals("default")){
            Toast.makeText(this, "You are signed in as default! Click the user icon to change your name!", Toast.LENGTH_SHORT).show();
        }
        getChats(); // run other setup stuff
        setupViews();
    }

    private void getChats(){
        // make list of chats of type ModelChat
        ArrayList<ChatModel> newChats = this.db.getAllMessages();

        if (chatAdapter == null) {
            chatAdapter = new ChatAdapter(this, new ArrayList<ChatModel>(), R.layout.chat_item);
        }
        //chatAdapter.addChats(newChats);
        //chatAdapter.notifyDataSetChanged();
        try {
            Log.e("GET", newChats.get(0).message);
        }catch(java.lang.IndexOutOfBoundsException e){
            Log.e("GET", "NO MESSAGES");
        }

    }

    private void setupViews(){
        ListView chatList = (ListView) findViewById(R.id.main_output_layout);
        chatList.setAdapter(chatAdapter);
        chatList.setOnItemClickListener(ClickListeners.clickChatListener(this, chatAdapter));

        final EditText input = (EditText) findViewById(R.id.main_input_entry);
        input.clearFocus();

        Button sendButton = (Button) findViewById(R.id.main_input_button);
        sendButton.setOnClickListener(ClickListeners.sendButtonListener(this,chatAdapter));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_change_username:
                ClickListeners.changeUsernameListener(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}