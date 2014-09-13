package com.uwanttolearn.com;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.uwanttolearn.com.db.DBContract;
import com.uwanttolearn.com.db.DBHelper;

/**
 * Created by waleed on 24/08/2014.
 */
public class DBUnitTest extends AndroidTestCase {



    public void testCaller(){
        createDB();
        addData();
        getData();
    }

    private void createDB(){
        mContext.deleteDatabase(DBHelper.DATABASE_NAME);
        SQLiteDatabase db = getSqLiteDatabase();

        assertTrue(db.isOpen());
        db.close();
        assertFalse(db.isOpen());

    }

    private void addData(){

        SQLiteDatabase db = getSqLiteDatabase();
        ContentValues contentValues = getContentValues();

        long id = db.insert(DBContract.Info.TABLE_NAME,null,contentValues);
        assertTrue( id != -1);

    }



    private void getData(){

        SQLiteDatabase db = getSqLiteDatabase();
        ContentValues contentValues = getContentValues();

        Cursor c = db.query(DBContract.Info.TABLE_NAME,null,null,null,null,null,null);
        assertTrue(c.moveToFirst());

        int nameIndex = c.getColumnIndex(DBContract.Info.COLUMN_NAME);
        int ageIndex = c.getColumnIndex(DBContract.Info.COLUMN_AGE);

        String actualName = c.getString(nameIndex);
        String actualAge = c.getString(ageIndex);


        String expectedName = contentValues.getAsString(DBContract.Info.COLUMN_NAME);
        String expectedAge = contentValues.getAsString(DBContract.Info.COLUMN_AGE);

        assertEquals(expectedName,actualName);
        assertEquals(expectedAge,actualAge);

        assertFalse(c.moveToNext());

    }


    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Info.COLUMN_NAME,"U Want To Learn");
        contentValues.put(DBContract.Info.COLUMN_AGE,"1 month");
        return contentValues;
    }

    private SQLiteDatabase getSqLiteDatabase() {
        DBHelper mDBHelper= new DBHelper(getContext());
        return mDBHelper.getWritableDatabase();
    }

}

