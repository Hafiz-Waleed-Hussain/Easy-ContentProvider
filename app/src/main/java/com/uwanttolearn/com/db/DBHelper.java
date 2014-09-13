package com.uwanttolearn.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by waleed on 24/08/2014.
 */
public class DBHelper extends SQLiteOpenHelper{

    //<editor-fold desc="Constants">
    public static final String DATABASE_NAME = "UWantToLearn.db";
    public static final int DATABASE_VERSION = 1;
    //</editor-fold>

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DBContract.Info.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DBContract.Info.SQL_DROP_TABE);
    }
}
