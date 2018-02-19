package com.example.a643.finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by 643 on 25/12/2017.
 */
@IgnoreExtraProperties
public class UserMovers extends User {

    String licenseType;
    String yearsDrive;
    String typeMover; // 0 = always, 1=sometimes

    public UserMovers()
    {

    }
    public UserMovers(String name, String phone, String email, String password, String licenseType, String yearsDrive, String typeMover)
    {
        super(name, phone, email, password);
        this.licenseType=licenseType;
        this.yearsDrive=yearsDrive;
        this.typeMover=typeMover;

    }
}
