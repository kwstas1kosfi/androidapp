package com.example.myhello;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tapToProceedTextView = findViewById(R.id.textView_tap_to_proceed);

        // Delayed execution to show "Tap to Proceed" after DELAY_TIME milliseconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tapToProceedTextView.setVisibility(View.VISIBLE);
            }
        }, DELAY_TIME);


        tapToProceedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //proceed to the next activity
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }
}
