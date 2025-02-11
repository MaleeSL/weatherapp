package com.example.weatherapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class loadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Animate Text Size
        TextView animatedText = findViewById(R.id.animatedText);
        ObjectAnimator textSizeAnimator = ObjectAnimator.ofFloat(animatedText, "textSize", 24f, 32f, 24f);
        textSizeAnimator.setDuration(6000);
        textSizeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        textSizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
        textSizeAnimator.start();

        // ProgressBar (No need for animation since it's indeterminate)
        ProgressBar progressBar = findViewById(R.id.progressBar);

        // Delay before switching to loggingPage
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(loadingPage.this, loggingPage.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
