package com.example.a643.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by 643 on 24/01/2018.
 */

public class AllAdsAdapter extends ArrayAdapter<Ad> {
    Context context;
    List<Ad> objects;


    public AllAdsAdapter (Context context, int resource, int textViewResourceId, List<Ad> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.adsview, parent, false);
        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
        TextView tvInfo = (TextView)view.findViewById(R.id.tvInfo);
        TextView tvPhone = (TextView)view.findViewById(R.id.tvPhone);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        ImageView ivProduct=(ImageView)view.findViewById(R.id.adImage);
        Ad temp = objects.get(position);
        tvTitle.setText(temp.nameProduct);
        tvInfo.setText(temp.info);
        tvPhone.setText(temp.phone);
        tvEmail.setText(temp.email);
        return view;

    }
    }
