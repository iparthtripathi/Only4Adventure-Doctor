package com.onlyforadventure.DocApp;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class userModel  {

    String name,email,specialization,phone;

    private FirebaseUser user;
    private DatabaseReference reference;

    public userModel() {
    }

    public userModel(String name, String email, String specialization, String phone) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }
}