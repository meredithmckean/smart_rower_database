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

    public static final String DATAFRAME33_INFO = "dataframe33_info";
    public static final String COLUMN_TIME_33 = "COLUMN_TIME_33";
    public static final String COLUMN_INTERVAL = "COLUMN_INTERVAL";
    public static final String COLUMN_POWER = "COLUMN_POWER";
    public static final String COLUMN_TOTAL_CAL = "COLUMN_TOTAL_CAL";
    public static final String COLUMN_SPLIT_PACE = "COLUMN_SPLIT_PACE";
    public static final String COLUMN_SPLIT_POWER = "COLUMN_SPLIT_POWER";
    public static final String COLUMN_SPLIT_CAL = "COLUMN_SPLIT_CAL";
    public static final String COLUMN_LAST_SPLIT_TIME = "COLUMN_LAST_SPLIT_TIME";
    public static final String COLUMN_LAST_SPLIT_DIST = "COLUMN_LAST_SPLIT_DIST";


    public static final String DATAFRAME35_INFO = "dataframe35_info";
    public static final String COLUMN_TIME_35 = "COLUMN_TIME_35";
    public static final String COLUMN_DIST = "COLUMN_DIST";
    public static final String COLUMN_DRIVE_LEN = "COLUMN_DRIVE_LEN";
    public static final String COLUMN_DRIVE_TIME = "COLUMN_DRIVE_TIME";
    public static final String COLUMN_STROKE_REC_TIME = "COLUMN_STROKE_REC_TIME";
    public static final String COLUMN_STROKE_DIST = "COLUMN_STROKE_DIST";
    public static final String COLUMN_PEAK_DRIVE_FORCE = "COLUMN_PEAK_DRIVE_FORCE";
    public static final String COLUMN_AVG_DRIVE_FORCE = "COLUMN_AVG_DRIVE_FORCE";
    public static final String COLUMN_WORK_PER_STROKE = "COLUMN_WORK_PER_STROKE";
    public static final String COLUMN_STROKE_COUNT = "COLUMN_STROKE_COUNT";


    //Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Smart_Rower_Tables.db", null, 7); //Everytime you change the
    }
    //methods that must be implemented

    //this is called the first time a database is accessed. There should be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_table = "Create TABLE " + USER_INFO + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_FTP + " INT, " + COLUMN_PZ_1 + " INT, " + COLUMN_PZ_2 + " INT, " + COLUMN_PZ_3 + " INT, " + COLUMN_PZ_4 + " INT, " + COLUMN_PZ_5 + " INT, " + COLUMN_PZ_6 + " INT, " + COLUMN_PZ_7 + " INT)";

        String dataframe33_table = "Create TABLE " + DATAFRAME33_INFO + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TIME_33 + " DOUB, " + COLUMN_INTERVAL + " INT, " + COLUMN_POWER + " INT, " + COLUMN_TOTAL_CAL + " INT, " + COLUMN_SPLIT_PACE + " INT, " + COLUMN_SPLIT_POWER + " INT, " + COLUMN_SPLIT_CAL + " INT, " + COLUMN_LAST_SPLIT_TIME + " INT, " + COLUMN_LAST_SPLIT_DIST + " INT)";

        String dataframe35_table = "Create TABLE " + DATAFRAME35_INFO + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TIME_35 + " DOUB, " + COLUMN_DIST + " DOUB, " + COLUMN_DRIVE_LEN + " DOUB, " + COLUMN_DRIVE_TIME + " DOUB, " + COLUMN_STROKE_REC_TIME + " DOUB, " + COLUMN_STROKE_DIST + " DOUB, " + COLUMN_PEAK_DRIVE_FORCE + " DOUB, " + COLUMN_AVG_DRIVE_FORCE + " DOUB, " + COLUMN_WORK_PER_STROKE + " DOUB, " + COLUMN_STROKE_COUNT + " INT)";


        db.execSQL(user_table);
        db.execSQL(dataframe33_table);
        db.execSQL(dataframe35_table);
    }

    //this is called if the database version number changes. It prevents users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table IF EXISTS USER_INFO");
        db.execSQL("Drop Table IF EXISTS DATAFRAME33_INFO");
        db.execSQL("Drop Table IF EXISTS DATAFRAME35_INFO");
        onCreate(db);
    }


    //Methods!!!

    //add to tables
    public boolean add_account(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        //If username exists return false
        Cursor cursor = db.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{user.getUsername()});  //Find user in user_table
        if (cursor.getCount() > 0) {
            return false;
        } else {
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

    public boolean add_dataframe33(dataframe33 dataframe33) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME_33, dataframe33.getTime_33());
        cv.put(COLUMN_INTERVAL, dataframe33.getInterval());
        cv.put(COLUMN_POWER, dataframe33.getPower());
        cv.put(COLUMN_TOTAL_CAL, dataframe33.getTotal_cal());
        cv.put(COLUMN_SPLIT_PACE, dataframe33.getSplit_pace());
        cv.put(COLUMN_SPLIT_POWER, dataframe33.getSplit_power());
        cv.put(COLUMN_SPLIT_CAL, dataframe33.getSplit_cal());
        cv.put(COLUMN_LAST_SPLIT_TIME, dataframe33.getLast_split_time());
        cv.put(COLUMN_LAST_SPLIT_DIST, dataframe33.getLast_split_dist());

        //ID is a auto increment in the database
        long insert = db.insert(DATAFRAME33_INFO, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean add_dataframe35(dataframe35 dataframe35) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TIME_35, dataframe35.getTime_35());
        cv.put(COLUMN_DIST, dataframe35.getDist());
        cv.put(COLUMN_DRIVE_LEN, dataframe35.getDrive_len());
        cv.put(COLUMN_DRIVE_TIME, dataframe35.getDrive_time());
        cv.put(COLUMN_STROKE_REC_TIME, dataframe35.getStroke_rec_time());
        cv.put(COLUMN_STROKE_DIST, dataframe35.getStroke_dist());
        cv.put(COLUMN_PEAK_DRIVE_FORCE, dataframe35.getPeak_drive_force());
        cv.put(COLUMN_AVG_DRIVE_FORCE, dataframe35.getAvg_drive_force());
        cv.put(COLUMN_WORK_PER_STROKE, dataframe35.getWork_per_stroke());
        cv.put(COLUMN_STROKE_COUNT, dataframe35.getStroke_count());
        //ID is a auto increment in the database
        long insert = db.insert(DATAFRAME35_INFO, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }


    //delete methods

    //delete one account from table
    public Boolean delete_account(String username, String password) //Username and password entered
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data

        String correct_password = null;
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(COLUMN_PASSWORD);
            correct_password = cursor.getString(index);
        }
        boolean password_match = password.equals(correct_password);


        if (cursor.getCount() > 0 & password_match) {
            long result = DB.delete(USER_INFO, "COLUMN_USER_NAME=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean delete_dataframe33_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DATAFRAME33_INFO);
        return true;
    }

    public Boolean delete_dataframe35_table() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DATAFRAME35_INFO);
        return true;
    }

    //updating methods

    public Boolean updateuserFTP(String username, int FTP, int pz_1, int pz_2, int pz_3, int pz_4, int pz_5, int pz_6, int pz_7) {
        SQLiteDatabase DB = this.getWritableDatabase();
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
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username}); // find in table
        if (cursor.getCount() > 0) {
            long result = DB.update(USER_INFO, cv, "COLUMN_USER_NAME = ?", new String[]{username}); //update table
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public Boolean updateuserPassword(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, password);
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username}); // find in table
        if (cursor.getCount() > 0) {
            long result = DB.update(USER_INFO, cv, "COLUMN_USER_NAME = ?", new String[]{username}); //update table
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //Other methods

    public boolean user_exists(String username) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Go getters for User

    public String getPassword(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PASSWORD);
                String password = cursor.getString(index);
                return password;
            }
        } else {
            return "no Password";
        }
        return "no Password";
    }

    public int getFTP(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int FTP_index = cursor.getColumnIndex("COLUMN_FTP");
                int FTP = cursor.getInt(FTP_index);
                return FTP;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_1(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_1);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_2(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_2);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_3(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_3);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }


    public int getPZ_4(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_4);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_5(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_5);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_6(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_6);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPZ_7(String username) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from user_info where COLUMN_USER_NAME = ?", new String[]{username});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PZ_7);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    //getters for databaseHelper33

    public int getInterval(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_INTERVAL);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getPower(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_POWER);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getTotal_cal(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_TOTAL_CAL);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getSplit_pace(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_SPLIT_PACE);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getSplit_power(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_SPLIT_POWER);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getSplit_cal(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_SPLIT_CAL);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getLast_split_time(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_LAST_SPLIT_TIME);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public int getLast_split_dist(double time33) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe33_info where COLUMN_TIME_33 = ?", new String[]{time33 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_LAST_SPLIT_DIST);
                int pz = cursor.getInt(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }


    //getters for databaseHelper35
    public double getDist(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_DIST);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getDrive_len(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_DRIVE_LEN);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getDrive_time(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_DRIVE_TIME);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getStroke_rec_time(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_STROKE_REC_TIME);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getStroke_dist(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_STROKE_DIST);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getPeak_drive_force(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PEAK_DRIVE_FORCE);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getAvg_drive_force(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_PEAK_DRIVE_FORCE);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getWork_per_stroke(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_WORK_PER_STROKE);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }

    public double getStroke_count(double time35) //Username and password entered
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from dataframe35_info where COLUMN_TIME_35 = ?", new String[]{time35 + ""});//Find the data
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int index = cursor.getColumnIndex(COLUMN_STROKE_COUNT);
                double pz = cursor.getDouble(index);
                return pz;
            }
        } else {
            return -1;
        }
        return -1;
    }



}



