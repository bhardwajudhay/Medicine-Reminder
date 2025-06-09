package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlarmRingActivity extends AppCompatActivity {

    @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }

        getWindow().setFlags(
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        android.view.WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        android.view.WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        );

        setContentView(R.layout.activity_alarm_ring);

            Button stopButton = findViewById(R.id.stopButton);
            stopButton.setOnClickListener(v -> {
                // Stop the alarm service
                Intent serviceIntent = new Intent(this, AlarmService.class);
                stopService(serviceIntent);
                finish(); // Close activity
            });
        }
    }
