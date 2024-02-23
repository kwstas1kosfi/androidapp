package com.example.myhello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        // Initialize the SQLiteDatabase instance
        db = openOrCreateDatabase("mydatabase.db", MODE_PRIVATE, null);

        createTable();

        final TextView buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert a new entry into the table
                insertDataIntoTable();
                // Proceed to the next activity
                startActivity(new Intent(UserActivity.this, Questions.class));
            }
        });
    }

    private void createTable() {
        // SQL statement to create a table with id, full name, and semester columns
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                + "acadecId INTEGER PRIMARY KEY,"
                + "full_name TEXT,"
                + "semester INTEGER);";

        // Execute the SQL statement
        db.execSQL(createTableSQL);
    }

    private void insertDataIntoTable() {
        // SQL statement to insert data into the table
        String insertDataSQL = "INSERT INTO students (academicId, full_name, semester) VALUES (?, ?, ?);";

        // Execute the SQL statement
        db.execSQL(insertDataSQL);
    }
}

