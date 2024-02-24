package com.example.myhello;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questions;
    private int currentQuestionIndex = 0;

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
    }

    private void showQuestion(Question question) {
        // Display the question text
        TextView questionTextView = findViewById(R.id.Question);
        questionTextView.setText(question.getText());

        // Display the options
        List<String> options = question.getOptions();
        LinearLayout optionsLayout = findViewById(R.id.optionsLayout);

        // Clear the options layout before adding new options
        optionsLayout.removeAllViews();

        for (int i = 0; i < options.size(); i++) {
            // Create a new TextView for each option
            TextView optionTextView = new TextView(this);
            optionTextView.setText(options.get(i));
            optionTextView.setTextSize(18); // Set text size as needed

            // Set click listener for each option
            optionTextView.setOnClickListener(view -> {
                // Check if the selected option is correct
                String selectedOption = ((TextView) view).getText().toString();
                String correctAnswer = question.getCorrectAnswer();
                if (selectedOption.equals(correctAnswer)) {
                    // Handle correct answer
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
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
                }
            });

            // Add the option TextView to the layout
            optionsLayout.addView(optionTextView);
        }
    }

}