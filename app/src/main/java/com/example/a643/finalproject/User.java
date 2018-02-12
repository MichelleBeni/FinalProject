package com.example.a643.finalproject;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by 643 on 19/12/2017.
 */
@IgnoreExtraProperties
public class User {
    public String name;
    public String phone;
    public String email;
    public String password;

     public User()
     {
     }
     public User(String name, String phone, String email, String password)
     {
         this.name=name;
         this.phone=phone;
         this.email=email;
         this.password=password;
     }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
