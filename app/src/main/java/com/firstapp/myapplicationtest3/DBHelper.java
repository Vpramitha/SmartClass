package com.firstapp.myapplicationtest3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final int Version = 1;
    private static final String DBName= "SchoolRecords";
    private static final String TableName = "StudentProfiles";
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateStudentProfilesTable = "CREATE TABLE StudentProfiles ( RowID INTEGER PRIMARY KEY AUTOINCREMENT, StudentID VARCHAR(30), StudentName VARCHAR(50), StudentGrade INTEGER, StudentClass VARCHAR(5), StudentContact VARCHAR(20), StudentPassword VARCHAR(30));";
        String CreateStudentMarksTable = "CREATE TABLE StudentMarks (RecordID INTEGER PRIMARY KEY AUTOINCREMENT, RowID INTEGER, Year INTEGER, Term INTEGER, Sinhala INTEGER, Mathematica INTEGER, Science INTEGER, English INTEGER, History INTEGER, Art INTEGER, FOREIGN KEY (RowID) REFERENCES StudentProfiles(RowID));";
        String CreateTeacherProfilesTable = "CREATE TABLE TeacherProfiles ( RowID INTEGER PRIMARY KEY AUTOINCREMENT, TeacherID VARCHAR(30), TeacherName VARCHAR(50), TeacherNIC VARCHAR(25), TeacherContact VARCHAR(20), TeacherPassword VARCHAR(30));";

        db.execSQL(CreateStudentProfilesTable);
        db.execSQL(CreateStudentMarksTable);
        db.execSQL(CreateTeacherProfilesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
        String DropTable = "DROP TABLE IF EXISTS " + TableName;
        db.execSQL(DropTable);
        onCreate(db);
    }

    public boolean AddAccount(DBModel dbMod){
        try{
        SQLiteDatabase sqliteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("StudentID",dbMod.getStudentId());
        contentValues.put("StudentName",dbMod.getStudentName());
        contentValues.put("StudentGrade",dbMod.getStudentGrade());
        contentValues.put("StudentClass",dbMod.getStudentClass());
        contentValues.put("StudentContact",dbMod.getStudentContact());
        contentValues.put("StudentPassword",dbMod.getStudentPassword());

        sqliteDatabase.insert(TableName,null,contentValues);
        sqliteDatabase.close();

            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
