package com.uwanttolearn.com.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by waleed on 24/08/2014.
 */
public class DBContract {

    //<editor-fold desc="Constants">
    public static final String AUTHORITY = "com.uwanttolearn";
    public static final String PATH_INFO_TABLE = "info";
    //</editor-fold>
    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY);

    public static class Info implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_INFO_TABLE).build();

        //<editor-fold desc="Constants">
        public static final String TABLE_NAME = "UWantToLearn_Info_Table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AGE = "age";
        //</editor-fold>

        //<editor-fold desc="Queries">
        public static final String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"( "
                +_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_NAME+" TEXT NOT NULL,"
                +COLUMN_AGE+" TEXT NOT NULL);";

        public static final String SQL_DROP_TABE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        //</editor-fold>
    }
}
