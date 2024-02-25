package com.example.myhello;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private ProgressBar progressBar;
    private SQLiteDatabase db;

    private int score = 0;


    // Initialize your XMLPullParser and parse the questions from your XML file
    // Load questions from the XML file (you might want to do this in onCreate())

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        // Retrieve questions from XML file
        questions = XmlParserQuestion.parseQuestions(getResources(), R.xml.questions);
        // Show Questions
        showQuestion(questions.get(currentQuestionIndex));
        db = UserActivity.db;

        // Initialize progress bar
        progressBar = findViewById(R.id.progressBar);
    }

    private void showQuestion(Question question) {
        // Display the question text
        TextView questionTextView = findViewById(R.id.Question);
        questionTextView.setText(question.getText());

        // Display the options
        List<String> options = question.getOptions();
        LinearLayout optionsLayout = findViewById(R.id.optionsLayout);

        // Set initial margin for the first option
        int marginBetweenOptions = 64; // Set your desired margin between options
        int marginTopForFirstOption = 0; // Set your desired top margin for the first option
        boolean firstOption = true;


        // Clear the options layout before adding new options
        optionsLayout.removeAllViews();

        for (int i = 0; i < options.size(); i++) {
            // Create a new TextView for each option
            TextView optionTextView = new TextView(this);
            optionTextView.setText(options.get(i));
            optionTextView.setTextSize(18); // Set text size as needed

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, firstOption ? marginTopForFirstOption : marginBetweenOptions, 0, 0);
            optionTextView.setLayoutParams(params);

            // Set click listener for each option
            optionTextView.setOnClickListener(view -> {
                // Check if the selected option is correct
                String selectedOption = ((TextView) view).getText().toString();
                String correctAnswer = question.getCorrectAnswer();
                if (selectedOption.equals(correctAnswer)) {
                    // Handle correct answer
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    score++;
                    // Update progress bar
                    updateProgressBar();
                } else {
                    // Handle incorrect answer
                    Toast.makeText(this, "Incorrect. The correct answer is " + correctAnswer, Toast.LENGTH_SHORT).show();
                }

                // Move to the next question
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion(questions.get(currentQuestionIndex));
                } else {
                    // Display quiz completion message or navigate to next activity
                    Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_SHORT).show();
                    updateScoreInDatabase(score);
                }
            });

            // Add the option TextView to the layout
            optionsLayout.addView(optionTextView);
            firstOption = false;
        }
    }

    private void updateProgressBar() {
        // Update progress bar
        int totalQuestions = questions.size();
        int answeredQuestions = currentQuestionIndex + 1; // Index starts from 0
        int progress = (answeredQuestions ) / totalQuestions;
        progressBar.setProgress(progress);
    }
    private void updateScoreInDatabase(int score) {
        // SQL statement to update the score for the current user
        String updateScoreSQL = "UPDATE students SET score =" + score + " WHERE academicId = ?";

        // Execute the SQL statement
        db.execSQL(updateScoreSQL);
    }


}