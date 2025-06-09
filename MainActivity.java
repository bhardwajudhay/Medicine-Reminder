package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageButton img1;
    ImageButton img2;
    Button btn1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        img1 = findViewById(R.id.imageButton);
        img2 = findViewById(R.id.imageButton2);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);

        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMedicine.class);
            startActivity(intent);
        }
        );
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmSetter.class);
            startActivity(intent);
        });
        img1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMedicine.class);
            startActivity(intent);
        }
        );
        img2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmSetter.class);
            startActivity(intent);
        }
        );
        }
    }
