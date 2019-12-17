package com.example.rvfirebasedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDB extends SQLiteOpenHelper {

    public static final String DB_name="MyDataBase";
    public static final String Table_Name="Books";
    public static final String book_Cover="cover";
    public static final String book_Name="bokkname";
    public static final String book_Author="author";
    public static final String book_Overview="bookoverview";
    public static final String book_Rate="rate";
    public static final String book_id="id";
    public static final String book_readingLink="read";
    public static final String book_downloadLink="download";
    static final int version1=1;

    public SqliteDB(Context context) {
        super(context, DB_name, null, version1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE = "CREATE TABLE " +  Table_Name  + " (" +
                book_id  + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                book_Cover  + " TEXT NOT NULL," +
                book_Name+ " TEXT NOT NULL, " +
                book_Author + " TEXT NOT NULL, " +
                book_Overview+ " TEXT NOT NULL, " +
                book_readingLink+ " TEXT NOT NULL, " +
                book_downloadLink+ " TEXT NOT NULL, " +
                book_Rate + " TEXT NOT NULL "+");";
        db.execSQL(SQL_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }
}
