package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


    public class NextActivity extends AppCompatActivity {

        Button NextButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_next);

            NextButton = findViewById(R.id.buttongetstarted);

            NextButton.setVisibility(View.INVISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    NextButton.setVisibility(View.VISIBLE);
                }
            }, 2500);

            NextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(NextActivity.this,MainActivity.class));
                    finish();
                }
            });
        }
    }


