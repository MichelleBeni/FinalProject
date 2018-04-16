package com.example.a643.finalproject;

/**
 * Created by 643 on 17/01/2018.
 */

public class Ad {
    public String info;
    public String nameProduct;
    public String email;
    public String phone;
    public String imageCode;
    public Ad()
    {

    }
    public Ad(String nameProduct, String info, String email, String phone, String imageCode)
    {
        this.nameProduct=nameProduct;
        this.info= info;
        this.email=email;
        this.phone=phone;
        this.imageCode=imageCode;

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

    public String getImageCode() {
        return imageCode;
    }
}
