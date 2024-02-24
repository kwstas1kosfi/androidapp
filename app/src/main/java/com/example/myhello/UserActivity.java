package com.example.myhello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

public class UserActivity extends AppCompatActivity {

    private static final String DB_NAME = "mydatabase.db";
    private SQLiteDatabase db;

    private EditText academicId;
    private EditText fullName;
    private EditText password;
    private EditText semester;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        // Initialize the SQLiteDatabase instance
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);

        // Create table if not exists
        createTable();

        // Initialize views
        academicId = findViewById(R.id.academic_id);
        fullName = findViewById(R.id.full_name);
        password = findViewById(R.id.password);
        semester = findViewById(R.id.semester);

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert data into the table
                insertDataIntoTable();
                // Proceed to the next activity
                startActivity(new Intent(UserActivity.this, QuestionActivity.class));
            }
        });
    }

    private void createTable() {
        // SQL statement to create a table with id, full name, and semester columns
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                + "academicId INTEGER PRIMARY KEY,"
                + "fullName TEXT,"
                + "password TEXT,"
                + "semester INTEGER,"
                + "score INTEGER);";

        // Execute the SQL statement
        db.execSQL(createTableSQL);
    }

    private void insertDataIntoTable() {
        // Get data from EditText fields
        String academicId = String.valueOf(this.academicId.getText());
        String fullName =  String.valueOf(this.fullName.getText());
        String passwordPlainText  =  String.valueOf(this.password.getText());
        String semester =  String.valueOf(this.semester.getText());

        String hashedPassword = BCrypt.hashpw(passwordPlainText, BCrypt.gensalt());

        // SQL statement to insert data into the table
        String insertDataSQL = "INSERT INTO students (academicId, fullName, password, semester, score) VALUES (?, ?, ?, ?, 0);";

        // Execute the SQL statement
        db.execSQL(insertDataSQL, new String[]{academicId,fullName, hashedPassword , semester});

        // Clear EditText fields after insertion
        this.academicId.setText("");
        this.fullName.setText("");
        this.password.setText("");
        this.semester.setText("");

        Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
    }
}

