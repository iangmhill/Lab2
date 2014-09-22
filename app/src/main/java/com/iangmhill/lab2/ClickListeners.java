package com.iangmhill.lab2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ClickListeners {

    public static View.OnClickListener sendButtonListener(final MyActivity activity, final ChatAdapter adapter){
        // stuff to do when button is clicked
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = ((EditText) activity.findViewById(R.id.main_input_entry));
                if (input.getText().toString().equals("")){
                    Toast.makeText(activity, "You didn't type anything in!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ChatModel newMessage = new ChatModel(MyActivity.username, input.getText().toString(), MyActivity.userId);
                adapter.addChat(newMessage);
                activity.db.addMessageToDatabase(newMessage);
                input.setText("");
            }
        };
    }

    public static void changeUsernameListener(final Activity activity){
        // stuff to do when change username button is clicked
        // create dialogue box of AlertDialog type (predefined)
        final EditText input = new EditText(activity);
        new AlertDialog.Builder(activity)
                .setTitle("Change Username")
                .setMessage("This is how you will show up to others.")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newName = input.getText().toString();
                        if (newName.equals("")) {
                            Toast.makeText(activity, "Discarded; can't be blank!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MyActivity.username = newName;
                        Toast.makeText(activity, "New username: " + newName, Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}