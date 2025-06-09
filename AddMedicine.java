package com.example.medicinereminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddMedicine extends AppCompatActivity {

    LinearLayout medicineContainer;
    Button addbtn, savebtn, reminderbtn;
    EditText editTextMedName, editTextDosage, editTextTime;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_medicine);
        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Medicines");

        medicineContainer = findViewById(R.id.linear);
        addbtn = findViewById(R.id.addbtn);
        savebtn = findViewById(R.id.savebtn);
        reminderbtn = findViewById(R.id.reminderbtn);

        editTextMedName = findViewById(R.id.editTextMedName);
        editTextDosage = findViewById(R.id.editTextDosage);
        editTextTime = findViewById(R.id.editTextTime);

        reminderbtn.setOnClickListener(v -> {
            Intent intent = new Intent(AddMedicine.this, AlarmSetter.class);
            startActivity(intent);
        });

        addbtn.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(this);
            View newMedicineRow = inflater.inflate(R.layout.dynamic_add_meds, medicineContainer, false);

            int index = medicineContainer.indexOfChild(addbtn); // insert before Add More button
            medicineContainer.addView(newMedicineRow, index);
        });

        savebtn.setOnClickListener(v -> {
            List<Medicine> medicines = new ArrayList<>();

            // Add initial fields
            String medName = editTextMedName.getText().toString().trim();
            String dosage = editTextDosage.getText().toString().trim();
            String time = editTextTime.getText().toString().trim();
            if (!medName.isEmpty() && !dosage.isEmpty() && !time.isEmpty()) {
                medicines.add(new Medicine(medName, dosage, time));
            }

            // Add dynamic fields
            for (int i = 0; i < medicineContainer.getChildCount(); i++) {
                View child = medicineContainer.getChildAt(i);
                EditText etMedName = child.findViewById(R.id.editTextMedName);
                EditText etDosage = child.findViewById(R.id.editTextDosage);
                EditText etTime = child.findViewById(R.id.editTextTime);

                if (etMedName != null && etDosage != null && etTime != null) {
                    String dynMedName = etMedName.getText().toString().trim();
                    String dynDosage = etDosage.getText().toString().trim();
                    String dynTime = etTime.getText().toString().trim();

                    if (!dynMedName.isEmpty() && !dynDosage.isEmpty() && !dynTime.isEmpty()) {
                        medicines.add(new Medicine(dynMedName, dynDosage, dynTime));
                    }
                }
            }

            if (medicines.isEmpty()) {
                Toast.makeText(this, "No medicine data to save", Toast.LENGTH_SHORT).show();
                return;
            }

            final int[] saveCount = {0};
            final boolean[] hasError = {false};

            for (Medicine med : medicines) {
                String id = myRef.push().getKey();
                if (id != null) {
                    myRef.child(id).setValue(med)
                            .addOnSuccessListener(aVoid -> {
                                saveCount[0]++;
                                Log.d("FirebaseSave", "Saved: " + med.getName());
                                checkCompletion(saveCount[0], medicines.size(), hasError[0]);
                            })
                            .addOnFailureListener(e -> {
                                hasError[0] = true;
                                Log.e("FirebaseSave", "Error saving: " + e.getMessage());
                                checkCompletion(saveCount[0], medicines.size(), hasError[0]);
                            });
                } else {
                    Log.e("FirebaseSave", "ID generation failed");
                }
            }
        });
    }

    private void checkCompletion(int saved, int total, boolean hasError) {
        if (saved == total) {
            runOnUiThread(() -> {
                if (hasError) {
                    Toast.makeText(this, "Some medicines failed to save", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "All medicines saved successfully", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(this, AlarmSetter.class);
                startActivity(intent);
            });
        }
    }
}

