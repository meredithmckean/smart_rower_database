package com.example.smart_rower_demo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USER_INFO = "user_info";
    public static final String COLUMN_USER_NAME = "COLUMN_USER_NAME";
    public static final String COLUMN_FTP = "COLUMN_FTP";
    public static final String COLUMN_ID = "COLUMN_ID";
    public static final String COLUMN_PASSWORD = "COLUMN_PASSWORD";
    public static final String COLUMN_PZ_1 = "COLUMN_PZ1";
    public static final String COLUMN_PZ_2 = "COLUMN_PZ2";
    public static final String COLUMN_PZ_3 = "COLUMN_PZ3";
    public static final String COLUMN_PZ_4 = "COLUMN_PZ4";
    public static final String COLUMN_PZ_5 = "COLUMN_PZ5";
    public static final String COLUMN_PZ_6 = "COLUMN_PZ6";
    public static final String COLUMN_PZ_7 = "COLUMN_PZ7";




    //Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, "User.db", null, 2); //Everytime you change the
    }
    //methods that must be implemented

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "Create TABLE " + USER_INFO + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_FTP + " INT, " + COLUMN_PZ_1 + " INT, " + COLUMN_PZ_2 + " INT, " + COLUMN_PZ_3 + " INT, " + COLUMN_PZ_4 + " INT, " + COLUMN_PZ_5 + " INT, " + COLUMN_PZ_6 + " INT, " + COLUMN_PZ_7 + " INT)";
        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes. It prevents users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table IF EXISTS USER_INFO");
        onCreate(db);
    }


    //Methods!!!

    //add to table
    public boolean addOne(User user) {
        SQLiteDatabase db = this.getWritableDatabase();


        //If username exists return false
        Cursor cursor = db.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {user.getUsername()});  //Find user in user_table
        if (cursor.getCount() > 0) {
            return false;
        }

        else {
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_USER_NAME, user.getUsername());
            cv.put(COLUMN_PASSWORD, user.getPassword());
            cv.put(COLUMN_FTP, user.getFTP());
            cv.put(COLUMN_PZ_1, user.getPz_1());
            cv.put(COLUMN_PZ_2, user.getPz_2());
            cv.put(COLUMN_PZ_3, user.getPz_3());
            cv.put(COLUMN_PZ_4, user.getPz_4());
            cv.put(COLUMN_PZ_5, user.getPz_5());
            cv.put(COLUMN_PZ_6, user.getPz_6());
            cv.put(COLUMN_PZ_7, user.getPz_7());
            //ID is a auto increment in the database

            long insert = db.insert(USER_INFO, null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }

    }

    //delete account
    public Boolean delete_account(String username, String password) //Username and password entered
    {
        SQLiteDatabase DB =  this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0)
        {
            long result = DB.delete(USER_INFO,"COLUMN_USER_NAME=?", new String[] {username} );
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else
        {
            return false;
        }

    }

    public Boolean updateuserFTP(String username, int FTP, int pz_1, int pz_2, int pz_3, int pz_4, int pz_5, int pz_6, int pz_7)
    {
        SQLiteDatabase DB =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_FTP, FTP);
        cv.put(COLUMN_PZ_1, pz_1);
        cv.put(COLUMN_PZ_2, pz_2);
        cv.put(COLUMN_PZ_3, pz_3);
        cv.put(COLUMN_PZ_4, pz_4);
        cv.put(COLUMN_PZ_5, pz_5);
        cv.put(COLUMN_PZ_6, pz_6);
        cv.put(COLUMN_PZ_7, pz_7);


        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username}); // find in table
        if (cursor.getCount() > 0)
        {
            long result = DB.update(USER_INFO,cv,"COLUMN_USER_NAME = ?", new String[] {username} ); //update table
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else
        {
            return false;
        }
    }


    public Boolean updateuserPassword(String username, String password)
    {
        SQLiteDatabase DB =  this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, password);


        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username}); // find in table
        if (cursor.getCount() > 0)
        {
            long result = DB.update(USER_INFO,cv,"COLUMN_USER_NAME = ?", new String[] {username} ); //update table
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else
        {
            return false;
        }
    }

    //Go getters

    public String getPassword(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PASSWORD);
                String password = cursor.getString(index);
                return password;

            }
        } else
        {
            return "no Password";
        }
        return "no Password";
    }

    public int getFTP(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int FTP_index = cursor.getColumnIndex("COLUMN_FTP");
                int FTP = cursor.getInt(FTP_index);
                return FTP;

            }
        } else
        {
            return -1;
        }
        return -1;

    }

    public int getPZ_1(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_1);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public int getPZ_2(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_2);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public int getPZ_3(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_3);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }


    public int getPZ_4(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_4);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public int getPZ_5(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_5);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public int getPZ_6(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_6);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public int getPZ_7(String username) //Username and password entered
    {
        SQLiteDatabase DB =  this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

        if (cursor.getCount() > 0){
            while(cursor.moveToNext()){

                int index = cursor.getColumnIndex(COLUMN_PZ_7);
                int pz = cursor.getInt(index);
                return pz;

            }
        } else
        {
            return -1;
        }
        return -1;
    }

    public boolean user_exists(String username) {
            SQLiteDatabase DB =  this.getReadableDatabase();

            Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[] {username});//Find the data

            if (cursor.getCount() > 0)
            {
                    return true;

            }else
            {
                return false;
            }
    }


}
