package com.example.medicinereminder;

public class Medicine {
    private String name;
    private String dosage;
    private String time;

    // Required by Firebase
    public Medicine() {
    }

    public Medicine(String name, String dosage, String time) {
        this.name = name;
        this.dosage = dosage;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDosage() {
        return dosage;
    }

    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
