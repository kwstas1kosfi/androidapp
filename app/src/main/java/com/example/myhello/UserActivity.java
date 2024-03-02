package com.example.myhello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private Data db;
    private EditText academicId;
    private EditText fullName;
    private EditText semester;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        db = new Data(this);

        // Initialize views
        academicId = findViewById(R.id.academic_id);
        fullName = findViewById(R.id.full_name);
        semester = findViewById(R.id.semester);

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDataIntoTable();
                Intent intent = new Intent(UserActivity.this, QuestionActivity.class);
                intent.putExtra("ACADEMIC_ID", academicId.getText().toString());
                // Proceed to the next activity
//                startActivity(new Intent(UserActivity.this, QuestionActivity.class));
                startActivity(intent);
            }
        });
    }

    private void insertDataIntoTable() {

        // Get data from EditText fields
        String academicId = String.valueOf(this.academicId.getText());
        String fullName =  String.valueOf(this.fullName.getText());
        String semester =  String.valueOf(this.semester.getText());
        int score = 0; // Set initial score

        if (academicId.isEmpty() || fullName.isEmpty() || semester.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(academicId, fullName, semester, score);

        long result = db.insertStudent(student);

        // Check if the insertion was successful
        if (result == -1) {
            Toast.makeText(this, "Failed to insert data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            // Clear EditText fields after successful insertion
            student.setAcademicId("");
            student.setFullName("");
            student.setSemester("");
        }
        new Handler().postAtTime(new Runnable() {
            @Override
            public void run() {
                System.out.println("h");
            }
        },3000);
    }


}
