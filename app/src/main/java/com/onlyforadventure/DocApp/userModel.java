package com.onlyforadventure.DocApp;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class userModel  {

    String name,email,specialization,phone, uid,img;

    private FirebaseUser user;
    private DatabaseReference reference;

    public userModel() {
    }

    public userModel(String name, String email, String specialization, String phone ,String img, String Uid) {
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.phone = phone;
        this.uid  = Uid;
        this.img=img;
    }
    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getImg() { return img; }

    public void setUid(String uid) {
        this.uid = uid;
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