package com.example.myhello;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Data extends SQLiteOpenHelper {

    private static final String DB_NAME = "quiz_database";
    private static final int DB_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COL_ACADEMIC_ID = "academicId";
    private static final String COL_FULL_NAME = "fullName";
    private static final String COL_SEMESTER = "semester";
    private static final String COL_SCORE = "score";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE IF NOT EXISTS  " + TABLE_STUDENTS + " (" +
            COL_ACADEMIC_ID + " INTEGER PRIMARY KEY," +
            COL_FULL_NAME + " TEXT," +
            COL_SEMESTER + " INTEGER," +
            COL_SCORE + " INTEGER)";

    public Data(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implement if you need to handle database upgrades
    }

    public long insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ACADEMIC_ID, student.getAcademicId());
        values.put(COL_FULL_NAME, student.getFullName());
        values.put(COL_SEMESTER, student.getSemester());
        values.put(COL_SCORE, student.getScore());
        db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return 0;
    }

    public void updateScore(String academicId, String newScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_SCORE, newScore);

        // Update the score for the given academic ID
        db.update(TABLE_STUDENTS, values, COL_ACADEMIC_ID + " = ?",
                new String[]{String.valueOf(academicId)});

        db.close();
    }
}
