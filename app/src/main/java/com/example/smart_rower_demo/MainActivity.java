package com.example.smart_rower_demo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//testing
//tetsing

public class MainActivity extends AppCompatActivity {

    // references of buttons and other controls on the layout
    EditText et_username, et_password;
    Button btn_login, btn_create_account, btn_delete_account, btn_user_info, btn_class_testing, btn_update_password, btn_update_FTP, btn_delete_tables, btn_history_error_tables, btn_view_history_error_tables;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //starts the application
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find data in app
        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);

        btn_login  = findViewById(R.id.btnLogin);
        btn_create_account = findViewById(R.id.btnCreateAccount);
        btn_delete_account = findViewById(R.id.btnDelete);
        btn_user_info = findViewById(R.id.btnUserInfo);
        btn_class_testing = findViewById(R.id.btnClassTesting);
        btn_update_password = findViewById(R.id.btnUpdatePassword);
        btn_update_FTP = findViewById(R.id.btnUpdateFTP);
        btn_delete_tables = findViewById(R.id.btnDeleteTables);
        btn_history_error_tables = findViewById(R.id.btnHistoryErrorTables);
        btn_view_history_error_tables = findViewById(R.id.btnViewHistoryErrorTables);


        //Test Data
        int FTP = 1;
        int pz_1 = 1;
        int pz_2 = 2;
        int pz_3 = 3;
        int pz_4 = 4;
        int pz_5 = 5;
        int pz_6 = 6;
        int pz_7 = 7;

        double time_33 = 1.0;
        int interval = 2;
        int power = 3;
        int total_cal = 4;
        double split_pace = 5.0;
        int split_power = 6;
        double split_cal = 7.0;
        double last_split_time = 8.0;
        double last_split_dist = 9.0;

        double time_35 = 10.0;
        double dist = 11.0;
        double drive_len = 12.0;
        double drive_time = 13.0;
        double stroke_rec_time = 14.0;
        double stroke_dist = 15.0;
        double peak_drive_force = 16.0;
        double avg_drive_force = 17.0;
        double work_per_stroke = 18.0;
        int stroke_count = 19;



        //button listeners for the add and view all buttons
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                boolean user_exists = db.user_exists(usernameTXT);
                if (user_exists == true){
                    Toast.makeText(MainActivity.this, "Username in System", Toast.LENGTH_SHORT).show();
                    boolean password_match = (passwordTXT.equals(db.getPassword(usernameTXT)));
                    if (password_match){
                        Toast.makeText(MainActivity.this, "Password Matched", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Password Does Not Matched", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, "Username not in System", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user;
                try {
                    user = new User(et_username.getText().toString(),et_password.getText().toString(),FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7); // Fill in class constructor
                    //Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show(); //Testing
                }

                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating Account", Toast.LENGTH_SHORT).show();
                    user = new User("error", "error",FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7);
                }

                //add User information in table "User_Info"
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this); //making reference to database
                boolean success = databaseHelper.add_account(user);

                if (success == true) {
                    Toast.makeText(MainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Account is not Created", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();
                Boolean checkdeletedata = db.delete_account(usernameTXT, passwordTXT);
                if(checkdeletedata == true)
                    Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Account not Deleted ", Toast.LENGTH_SHORT).show();
            }
        });

        btn_user_info.setOnClickListener(new View.OnClickListener() {  //Display Information
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //Testing db's go getters
                int FTP = db.getFTP(usernameTXT);
                Toast.makeText(MainActivity.this, "FTP: " + FTP, Toast.LENGTH_SHORT).show();

                //testing user_info table go getters
                int pz1 = db.getPZ_1(usernameTXT);
                int pz2 = db.getPZ_2(usernameTXT);
                int pz3 = db.getPZ_3(usernameTXT);
                int pz4 = db.getPZ_4(usernameTXT);
                int pz5 = db.getPZ_5(usernameTXT);
                int pz6 = db.getPZ_6(usernameTXT);
                int pz7 = db.getPZ_7(usernameTXT);
                Toast.makeText(MainActivity.this, "pz: " + pz1 +' '+pz2 +' '+pz3 +' '+pz4 +' '+pz5 +' '+pz6 +' '+pz7, Toast.LENGTH_SHORT).show();

                //testing dataframe33_info table go getters
                //int interval = db.getInterval();
                int interval = db.getInterval();
                int power = db.getPower();
                int total_cal = db.getTotal_cal();
                int split_pace = db.getSplit_pace();
                int split_power = db.getSplit_power();
                int split_cal = db.getSplit_cal();
                int last_split_time = db.getLast_split_time();
                int last_split_dist = db.getLast_split_dist();
                Toast.makeText(MainActivity.this, "dataframe33: " + interval +' '+ power +' '+ total_cal +' '+ split_pace +' '+ split_power +' '+ split_cal +' '+ last_split_time+' '+ last_split_dist, Toast.LENGTH_SHORT).show();

                //testing dataframe35_info table go getters
                double dist = db.getDist();
                double drive_len = db.getDrive_len();
                double drive_time = db.getDrive_time();
                double stroke_dist = db.getStroke_dist();
                double peak_drive_force = db.getPeak_drive_force();
                double avg_drive_force = db.getAvg_drive_force();
                double work_per_stroke = db.getWork_per_stroke();
                double stroke_count = db.getStroke_count();
                Toast.makeText(MainActivity.this, "dataframe35: " + dist +' '+ drive_len +' '+ drive_time +' '+ stroke_dist +' '+ peak_drive_force +' '+ avg_drive_force +' '+ work_per_stroke +' '+ stroke_count, Toast.LENGTH_SHORT).show();
            }
        });


        btn_class_testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Testing User class
                User user = new User(et_username.getText().toString(),et_password.getText().toString(),FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7); // Fill in class constructor
                Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();


                //Testing Real Time data classes
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this); //making reference to database

                //Testing dataframe33
                dataframe33 realdata1 = new dataframe33(time_33, interval, power, total_cal, split_pace, split_power, split_cal,last_split_time, last_split_dist);
                Toast.makeText(MainActivity.this, realdata1.toString(), Toast.LENGTH_SHORT).show(); //Testing
                boolean success1 = databaseHelper.add_dataframe33(realdata1);
                if (success1 == true){
                    Toast.makeText(MainActivity.this, "Successfully entered table", Toast.LENGTH_SHORT).show(); //Testing
                }
                else{
                    Toast.makeText(MainActivity.this, "Did not enter table", Toast.LENGTH_SHORT).show(); //Testing
                }


                //Testing dataframe35
                dataframe35 realdata2 = new dataframe35(time_35, dist, drive_len, drive_time, stroke_rec_time, stroke_dist, peak_drive_force, avg_drive_force, work_per_stroke, stroke_count);
                Toast.makeText(MainActivity.this, realdata2.toString(), Toast.LENGTH_SHORT).show(); //Testing
                boolean success2 = databaseHelper.add_dataframe35(realdata2);
                if (success2 == true){
                    Toast.makeText(MainActivity.this, "Successfully entered table", Toast.LENGTH_SHORT).show(); //Testing
                }
                else{
                    Toast.makeText(MainActivity.this, "Did not enter table", Toast.LENGTH_SHORT).show(); //Testing
                }

            }
        });

        btn_update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //Testing update password
                boolean success = db.updateuserPassword(et_username.getText().toString(), passwordTXT);
                if (success == true){
                    Toast.makeText(MainActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Password is not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_update_FTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //Testing Updating FTP
                boolean success2 = db.updateuserFTP(et_username.getText().toString(),8, 9, 10, 11, 12, 13, 14, 15);
                if (success2 == true){
                    Toast.makeText(MainActivity.this, "FTP Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "FTP is not Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_delete_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                boolean success1 = db.delete_dataframe33_table();
                Toast.makeText(MainActivity.this, "dataframe33 table Deleted", Toast.LENGTH_SHORT).show();
                boolean success2 = db.delete_dataframe35_table();
                Toast.makeText(MainActivity.this, "dataframe35 table Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        btn_history_error_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //Testing adding to history table
                boolean success1 = db.add_history(usernameTXT,"workout1");
                if (success1 == true){
                    Toast.makeText(MainActivity.this, "workout added to history table", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "workout not added to history table", Toast.LENGTH_SHORT).show();
                }


                //Testing adding to error table
                int error = 5;
                boolean success2 = db.add_error(usernameTXT,error);
                if (success2 == true){
                    Toast.makeText(MainActivity.this, "error added to history table", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "error not added to history table", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_view_history_error_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //display history table user specific
                Cursor res = db.get_history(usernameTXT);
                if(res.getCount() == 0) {
                    Toast.makeText(MainActivity.this,"No History Exists", Toast.LENGTH_SHORT).show();
                    res.close();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    //buffer.append("timestamp :"+res.getString(2)+"\n");
                    //buffer.append("Workout :"+res.getString(3)+"\n");
                    buffer.append(res.getString(2)+ "    "+"Workout :"+res.getString(3)+"\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle(usernameTXT+" Workouts");
                builder.setMessage(buffer.toString());
                builder.show();
                res.close();


                //display history table user specific
                Cursor res2 = db.get_error(usernameTXT);
                if(res2.getCount() == 0) {
                    Toast.makeText(MainActivity.this,"No Error Exists", Toast.LENGTH_SHORT).show();
                    res2.close();
                    return;
                }
                StringBuffer buffer2 = new StringBuffer();
                while(res2.moveToNext()) {
                    buffer2.append(res2.getString(2)+"   "+"Errors: "+res2.getString(3)+"\n");
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setCancelable(true);
                builder2.setTitle(usernameTXT+" Errors");
                builder2.setMessage(buffer2.toString());
                builder2.show();
                res2.close();
            }
        });



    }
}