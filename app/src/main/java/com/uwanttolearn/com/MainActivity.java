package com.uwanttolearn.com;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.uwanttolearn.com.db.DBContract;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    //<editor-fold desc="Constants">
    private static final int CURSOR_LOADER_ID = 1;
    private static  final String[] FROM = new String[]{DBContract.Info.COLUMN_NAME, DBContract.Info.COLUMN_AGE};
    private static final int[] TO = new int[]{R.id.MainActivity_list_row_name_text_view, R.id.MainActivity_list_row_age_text_view};
//</editor-fold>

    //<editor-fold desc="Views">
    private EditText mNameEditText;
    private EditText mAgeEditText;
    //</editor-fold>

    //<editor-fold desc="Adapter (Communicator between Data and GUI)">
    private CursorAdapter mCursorAdapter;
    //</editor-fold>

    //<editor-fold desc="Activity Life Cycle Methods">

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setUp();
    }

    //</editor-fold>

    //<editor-fold desc="Cursor Loader Implemented Methods">
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DBContract.Info.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mCursorAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mCursorAdapter.changeCursor(null);
    }
    //</editor-fold>

    //<editor-fold desc="Button Click Listeners">
    public void onAddClick(View view){

        if( validateEditText(mNameEditText) == false || validateEditText(mAgeEditText) == false){
            showIncompleteInformationToast();
            return;
        }

        Uri uri = getContentResolver().insert(DBContract.Info.CONTENT_URI,getContentValues());
        long id = ContentUris.parseId(uri);
        checkIsDataAdded(id);

    }


    public void onUpdateClick(View view){

        if(validateEditText(mNameEditText) == false){
            showIncompleteInformationToast();
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(DBContract.Info.COLUMN_NAME,mNameEditText.getText().toString());
        cv.put(DBContract.Info.COLUMN_AGE,mAgeEditText.getText().toString());
        int numberOfRowsUpdated = getContentResolver().update(DBContract.Info.CONTENT_URI,cv, DBContract.Info.COLUMN_NAME+" = ?",new String[]{cv.getAsString(DBContract.Info.COLUMN_NAME)});
        checkIsDateUpdated(numberOfRowsUpdated);
    }


    public void onDeleteClick(View view){

        if(validateEditText(mNameEditText) == false){
            showIncompleteInformationToast();
            return;
        }
        int numberOfRowsDeleted = getContentResolver().delete(DBContract.Info.CONTENT_URI, DBContract.Info.COLUMN_NAME+" = ?", new String[]{mNameEditText.getText().toString()});
        checkIsDataDeleted(numberOfRowsDeleted);
    }
    //</editor-fold>

    //<editor-fold desc="Private Methods (Helper methods)">
    private void init() {
        mNameEditText = (EditText) findViewById(R.id.MainActivity_enter_name_edit_text);
        mAgeEditText = (EditText) findViewById(R.id.MainActivity_enter_age_edit_text);
        mCursorAdapter = new SimpleCursorAdapter(this,R.layout.list_row,null, FROM, TO,0);
        getLoaderManager().initLoader(CURSOR_LOADER_ID,null,this);
    }

    private void setUp() {
        setListAdapter(mCursorAdapter);
    }


    private ContentValues getContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Info.COLUMN_NAME, mNameEditText.getText().toString());
        cv.put(DBContract.Info.COLUMN_AGE, mAgeEditText.getText().toString());
        clearEditText(mNameEditText);
        clearEditText(mAgeEditText);
        return cv;
    }

    // Show Toast messages methods
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showIncompleteInformationToast() {
        Toast.makeText(this, "Incomplete information", Toast.LENGTH_SHORT).show();
    }

    private void clearEditText(EditText forClearEditText){
        forClearEditText.setText("");
    }

    // Validation methods

    private void checkIsDateUpdated(int numberOfRowsUpdated) {
        String message = "Data not updated";
        if( numberOfRowsUpdated > 0)
            message = "Successfully Updated";

        showToast(message);

    }

    private void checkIsDataDeleted(int numberOfRowsDeleted) {
        String message = "No Data Deleted";
        if( numberOfRowsDeleted > 0)
            message = "Successfully Deleted";

        showToast(message);
    }

    private void checkIsDataAdded(long id) {
        String message = "New data not added";
        if( id != -1)
            message = "Successfully Added";

        showToast(message);
    }

    private boolean validateEditText(EditText forValidationEditText){
        String text = forValidationEditText.getText().toString();
        text.trim();
        if(text.length() > 0)
            return true;
        return false;
    }
    //</editor-fold>





    // Testing Android Studio Git
    // Testing Android Studio Git


}
