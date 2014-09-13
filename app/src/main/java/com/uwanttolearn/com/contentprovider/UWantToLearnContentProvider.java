package com.uwanttolearn.com.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.uwanttolearn.com.db.DBContract;
import com.uwanttolearn.com.db.DBHelper;

/**
 * Created by waleed on 24/08/2014.
 */
public class UWantToLearnContentProvider extends ContentProvider{

    //<editor-fold desc="Constants">
    private static final int ALL = 1;
    //</editor-fold>

    //<editor-fold desc="Class Members">
    private DBHelper mDBDbHelper;
    //</editor-fold>

    //<editor-fold desc="Statis Fields and Code block">
    private static UriMatcher mUriMatcher;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(DBContract.AUTHORITY,DBContract.PATH_INFO_TABLE, ALL);

    }
    //</editor-fold>

    @Override
    public boolean onCreate() {

        mDBDbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor c = null;
        int match = mUriMatcher.match(uri);
        switch (match){
            case ALL:
                SQLiteDatabase db = mDBDbHelper.getWritableDatabase();
                c = db.query(DBContract.Info.TABLE_NAME,null,null,null,null,null,null);
                break;
        }

        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Uri newUri = null;

        int match = mUriMatcher.match(uri);
        switch (match){
            case ALL:
                SQLiteDatabase db = mDBDbHelper.getWritableDatabase();
                long id = db.insert(DBContract.Info.TABLE_NAME,null,values);
                newUri = ContentUris.withAppendedId(uri,id);
                getContext().getContentResolver().notifyChange(newUri,null);
                break;
            default:
                throw new UnsupportedOperationException("Uri not defined : "+uri);
        }
        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBDbHelper.getWritableDatabase();
        int deletedRowId = db.delete(DBContract.Info.TABLE_NAME, selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return deletedRowId;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBDbHelper.getWritableDatabase();
        int numberOfRowsUpdated = db.update(DBContract.Info.TABLE_NAME,values,selection,selectionArgs);
        getContext().getContentResolver().notifyChange(uri,null);
        return numberOfRowsUpdated;
    }
}
