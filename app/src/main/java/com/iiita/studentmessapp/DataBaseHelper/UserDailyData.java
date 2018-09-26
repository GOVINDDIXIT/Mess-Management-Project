package com.iiita.studentmessapp.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDailyData extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserDailyData";
    public static final String TABLE_NAME = "SubmissionData";
    public static final String COL_1 = "DATE";
    public static final String COL_2 = "MEALTYPE";
    public static final String COL_3 = "RATING";

    public UserDailyData(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COL_1 + " TEXT, " + COL_2 + " TEXT," + COL_3 + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String date, int mealType, int rating) {
        long success = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, date);
        contentValues.put(COL_2, mealType);
        contentValues.put(COL_3, rating);
        if (!dataExistForADay(date, mealType)) {
            success = db.insert(TABLE_NAME, null, contentValues);
        }
        return success >= 0;
    }

    public boolean dataExistForADay(String date, int mealType) {
        Cursor cr = getAllData();
        cr.moveToFirst();
        if (cr != null && cr.getCount() > 0) {
            while (!cr.isAfterLast()) {
                String cursorCurrentDate = cr.getString(0);
                int cursorMealType = cr.getInt(1);
                if (cursorCurrentDate.equals(date) && (cursorMealType == mealType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("Select * from " + TABLE_NAME, null);
        return cr;
    }

}
