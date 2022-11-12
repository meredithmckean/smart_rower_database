package com.example.smart_rower_demo;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

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

public class MainActivity extends AppCompatActivity {

    // references of buttons and other controls on the layout
    EditText et_username, et_password;
    Button btn_login, btn_create_account, btn_delete_account, btn_user_info, btn_class_testing, btn_update_password, btn_update_FTP;

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
                    user = new User(-1,et_username.getText().toString(),et_password.getText().toString(),FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7); // Fill in class constructor
                    //Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show(); //Testing
                }

                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating Account", Toast.LENGTH_SHORT).show();
                    user = new User(-1,"error", "error",FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7);
                }

                //add User information in table "User_Info"
                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this); //making reference to database
                boolean success = databaseHelper.addOne(user);

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

        btn_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this); //making reference to database
                String usernameTXT = et_username.getText().toString();
                String passwordTXT = et_password.getText().toString();

                //Testing db's go getters
                int FTP = db.getFTP(usernameTXT);
                Toast.makeText(MainActivity.this, "FTP: " + FTP, Toast.LENGTH_SHORT).show();

                int pz1 = db.getPZ_1(usernameTXT);
                int pz2 = db.getPZ_2(usernameTXT);
                int pz3 = db.getPZ_3(usernameTXT);
                int pz4 = db.getPZ_4(usernameTXT);
                int pz5 = db.getPZ_5(usernameTXT);
                int pz6 = db.getPZ_6(usernameTXT);
                int pz7 = db.getPZ_7(usernameTXT);
                Toast.makeText(MainActivity.this, "pz: " + pz1 +' '+pz2 +' '+pz3 +' '+pz4 +' '+pz5 +' '+pz6 +' '+pz7, Toast.LENGTH_SHORT).show();

            }
        });


        btn_class_testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Testing User class
                User user = new User(-1,et_username.getText().toString(),et_password.getText().toString(),FTP, pz_1, pz_2, pz_3, pz_4, pz_5, pz_6, pz_7); // Fill in class constructor
                Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show();

                //Testing Real Time data classes
                dataframe33 realdata1 = new dataframe33(time_33, interval, power, total_cal, split_pace, split_power, split_cal,last_split_time, last_split_dist);
                Toast.makeText(MainActivity.this, realdata1.toString(), Toast.LENGTH_SHORT).show(); //Testing

                dataframe35 realdata2 = new dataframe35(time_35, dist, drive_len, drive_time, stroke_rec_time, stroke_dist, peak_drive_force, avg_drive_force, work_per_stroke, stroke_count);
                Toast.makeText(MainActivity.this, realdata2.toString(), Toast.LENGTH_SHORT).show(); //Testing

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




    }
}