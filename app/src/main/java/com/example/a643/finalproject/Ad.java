package com.example.a643.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by 643 on 17/01/2018.
 */

public class Ad {
    public String info;
    public String nameProduct;
    public String email;
    public String phone;
    public String id;

    public Ad()
    {

    }
    public Ad(String nameProduct, String info, String email, String phone)
    {
        this.nameProduct=nameProduct;
        this.info= info;
        this.email=email;
        this.phone=phone;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getEmail() {
        return email;
    }

    public String getInfo() {
        return info;
    }

    public String getPhone() {
        return phone;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
