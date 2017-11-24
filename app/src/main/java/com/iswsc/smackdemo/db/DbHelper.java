package com.iswsc.smackdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jacen on 2017/3/24 13:22.
 * jacen@iswsc.com
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;

    public DbHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        String sql_chat = "CREATE TABLE IF NOT EXISTS "
                + TableField._TABLE_CHAT + "( "
                + TableField._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TableField._FIELD_MESSAGE_ID + " text,"
                + TableField._FIELD_CHAT_JID + " text,"
                + TableField._FIELD_CONTENT + " text,"
                + TableField._FIELD_CHAT_TYPE + " integer,"
                + TableField._FIELD_SEND_TIME + " long,"
                + TableField._FIELD_SHOW_TIME + " integer,"
                + TableField._FIELD_IS_ME + " integer,"
                + TableField._FIELD_UNREAD + " integer,"
                + TableField._FIELD_MESSAGE_STATUS + " integer"
                + ")";
        db.execSQL(sql_chat);

    }

    private void dropTable(SQLiteDatabase db) {
        String sql_chat = "DROP TABLE IF EXISTS " + TableField._TABLE_CHAT;
        db.execSQL(sql_chat);
    }
}
