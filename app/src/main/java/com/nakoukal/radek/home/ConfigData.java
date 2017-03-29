package com.nakoukal.radek.home;

import android.provider.BaseColumns;

/**
 * Created by uidv7359 on 13.03.2017.
 */

public final class ConfigData {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ConfigData() {}

    /* Inner class that defines the table contents */
    public static class ConfigEntry implements BaseColumns {
        public static final String TABLE_NAME = "config";
        public static final String COLUMN_NAME_VALUE = "value";
        public static final String COLUMN_NAME_DATA = "data";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ConfigEntry.TABLE_NAME + " (" +
                    ConfigEntry._ID + " INTEGER PRIMARY KEY," +
                    ConfigEntry.COLUMN_NAME_VALUE + " TEXT," +
                    ConfigEntry.COLUMN_NAME_DATA + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ConfigEntry.TABLE_NAME;
}