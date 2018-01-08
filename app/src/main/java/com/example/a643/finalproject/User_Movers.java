package com.example.a643.finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by 643 on 25/12/2017.
 */
@IgnoreExtraProperties

public class User_Movers extends User {

    char licenseType;
    int yearsDrive;
    int typeMover; // 0 = always, 1=sometimes

    public User_Movers()
    {

    }
    public User_Movers(String name, String phone, String email, String password, char licenseType, int yearsDrive, int typeMover)
    {
        super(name, phone, email, password);
        this.licenseType=licenseType;
        this.yearsDrive=yearsDrive;
        this.typeMover=typeMover;

    }
}
