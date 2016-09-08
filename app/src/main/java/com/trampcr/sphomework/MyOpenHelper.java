package com.trampcr.sphomework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zxm on 2016/8/13.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "users.db";
    public static final int DB_VERSION = 1;
    public static final String CREATE_TABLE = "create table user(username varchar unique, password varchar, login_state text)";

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
