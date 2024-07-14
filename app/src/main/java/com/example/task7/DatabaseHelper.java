package com.example.task7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Notes(title TEXT primary key, content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Notes");
    }

    public Boolean insertNoteData(String title, String content) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("content", content);
        long result = DB.insert("Notes", null, contentValues);
        return result != -1;
    }

    public Boolean updateNoteData(String title, String content) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        Cursor cursor = DB.rawQuery("Select * from Notes where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.update("Notes", contentValues, "title=?", new String[]{title});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deleteData(String title) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Notes where title = ?", new String[]{title});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Notes", "title=?", new String[]{title});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Notes", null);
    }
}
