package com.example.myhello;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private ProgressBar progressBar;
    private int score = 0;
    private Data db;
    private String academicId;

    private static final long TIME_LIMIT_MILLIS = 600000; // 10 minutes
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        // Retrieve questions from XML file
        questions = XmlParserQuestion.parseQuestions(getResources(), R.xml.questions);
        // Show Questions
        showQuestion(questions.get(currentQuestionIndex));

        // Initialize progress bar
        progressBar = findViewById(R.id.progressBar);
        db = new Data(this);
        if (getIntent().hasExtra("ACADEMIC_ID")) {
            academicId = getIntent().getStringExtra("ACADEMIC_ID");
        } else {
        }
        startTimer();
    }

    private void showQuestion(Question question) {
        // Display the question text
        TextView questionTextView = findViewById(R.id.Question);
        questionTextView.setText(question.getText());

        // Display the image associated with the question
        ImageView questionImageView = findViewById(R.id.questionImage);
        String imageResourceName = question.getImageResourceName();
        int resourceId = getResources().getIdentifier(imageResourceName, "drawable", getPackageName());
        questionImageView.setImageResource(resourceId);

        // Display the options
        List<String> options = question.getOptions();
        LinearLayout optionsLayout = findViewById(R.id.optionsLayout);

        // Set initial margin for the first option
        int marginBetweenOptions = 64; // Set your desired margin between options
        int marginTopForFirstOption = 0; // Set your desired top margin for the first option
        boolean firstOption = true;

        // Clear the options layout before adding new options
        optionsLayout.removeAllViews();
        // Shuffle the options to display them randomly
        Collections.shuffle(questions);

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
                } else {
                    // Handle incorrect answer
                    Toast.makeText(this, "Incorrect. The correct answer is " + correctAnswer, Toast.LENGTH_SHORT).show();
                }
                // Update progress bar
                updateProgressBar();
                // Move to the next question
                new Handler().postDelayed(() -> {
                    // Move to the next question
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        showQuestion(questions.get(currentQuestionIndex));
                    } else {
                        db.updateScore(academicId, String.valueOf(score));
                        // Display quiz completion message or navigate to next activity
                        Toast.makeText(QuestionActivity.this, "Quiz Completed!", Toast.LENGTH_SHORT).show();
                    }
                }, 2300); // Delay in milliseconds (1000 milliseconds = 1 second)
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
        int progress = (answeredQuestions * 100) / totalQuestions;
        progressBar.setBackgroundColor(1);
        progressBar.setProgress(progress);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIME_LIMIT_MILLIS, 1000) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the UI with the remaining time

                TextView timerTextView = findViewById(R.id.timerTextView);
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                TextView timerTextView = findViewById(R.id.timerTextView);
                // Timer finished, handle the end of the time limit
                timerTextView.setText("Time's up!");
                // Add logic to handle the end of the time limit, e.g., show correct answer, move to the next question, etc.
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the countdown timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

}
