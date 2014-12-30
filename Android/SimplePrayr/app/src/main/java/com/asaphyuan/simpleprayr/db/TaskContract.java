package com.asaphyuan.simpleprayr.db;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.asaphyuan.simpleprayr.db.requests";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "requests";


    public class Columns {
        public static final String REQUEST = "requests";
        public static final String _ID = BaseColumns._ID;
    }
}