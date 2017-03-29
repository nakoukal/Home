package com.nakoukal.radek.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by uidv7359 on 17.03.2017.
 */

public class ConfigDB {
    private ConfigEntryDbHelper mDbHelper;


    public ConfigDB(Context context)
    {
        this.mDbHelper  = new ConfigEntryDbHelper(context);
    }

    public String GetData(String Name)
    {
        SQLiteDatabase dbr = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ConfigData.ConfigEntry._ID,
                ConfigData.ConfigEntry.COLUMN_NAME_VALUE,
                ConfigData.ConfigEntry.COLUMN_NAME_DATA
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = ConfigData.ConfigEntry.COLUMN_NAME_VALUE + " = ?";
        String[] selectionArgs = { Name };
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ConfigData.ConfigEntry.COLUMN_NAME_VALUE + " DESC";

        Cursor cursor = dbr.query(
                ConfigData.ConfigEntry.TABLE_NAME,        // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        Map<String,String> itemIds = new HashMap<String, String>();
        while(cursor.moveToNext()) {
            String value = cursor.getString(cursor.getColumnIndexOrThrow(ConfigData.ConfigEntry.COLUMN_NAME_VALUE));
            String data = cursor.getString(cursor.getColumnIndexOrThrow(ConfigData.ConfigEntry.COLUMN_NAME_DATA));
            itemIds.put(value,data);
        }
        cursor.close();

        if(itemIds.containsKey(Name))
            return itemIds.get(Name);
        else
            return "";

    }

    public void AddData(String value,String data)
    {
        if(GetData(value) == "")
            InsertData(value, data);
        else
            UpdateData(value, data);

    }

    private void InsertData(String value,String data)
    {
        // Gets the data repository in write mode
        SQLiteDatabase dbw = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ConfigData.ConfigEntry.COLUMN_NAME_VALUE, value );
        values.put(ConfigData.ConfigEntry.COLUMN_NAME_DATA, data);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = dbw.insert(ConfigData.ConfigEntry.TABLE_NAME, null, values);

        dbw.close();
    }

    private void UpdateData(String value,String data)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(ConfigData.ConfigEntry.COLUMN_NAME_DATA, data);

        // Which row to update, based on the title
        String selection = ConfigData.ConfigEntry.COLUMN_NAME_VALUE + " LIKE ?";
        String[] selectionArgs = { value };

        int count = db.update(
                ConfigData.ConfigEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

}
