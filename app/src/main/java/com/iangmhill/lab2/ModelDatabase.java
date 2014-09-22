package com.iangmhill.lab2;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by ihill on 9/21/2014.
 */
public class ModelDatabase extends SQLiteOpenHelper {


    //Table Name
    public static final String TABLE_NAME = "messages";

    //Table Fields
    public static final String MESSAGE_TIME = "time";
    public static final String MESSAGE_SENDER = "sender";
    public static final String MESSAGE_BODY = "body";
    public static final String MESSAGE_USERID = "userid";

    //Database Info
    private static final String DATABASE_NAME = "MessageDatabase";
    private static final int DATABASE_VERSION = 1;

    // ModelDatabase creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + MESSAGE_TIME + " TEXT NOT NULL UNIQUE, "
            + MESSAGE_SENDER + " TEXT NOT NULL, "
            + MESSAGE_BODY + " TEXT NOT NULL, "
            + MESSAGE_USERID + " TEXT NOT NULL );";

    //Default Constructor
    public ModelDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //OnCreate Method - creates the ModelDatabase
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);

    }
    @Override
    //OnUpgrade Method - upgrades ModelDatabase if applicable
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(ModelDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}

