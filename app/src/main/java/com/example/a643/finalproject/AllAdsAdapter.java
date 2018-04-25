package com.example.a643.finalproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

/**
 * Created by 643 on 24/01/2018.
 */

public class AllAdsAdapter extends ArrayAdapter<Ad> {
    Context context;
    List<Ad> objects;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference Ref = storage.getReference();


    public AllAdsAdapter(Context context, int resource, int textViewResourceId, List<Ad> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.adsview, parent, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        TextView tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        final ImageView ivProduct = (ImageView) view.findViewById(R.id.adImage);
        Ad temp = objects.get(position);
        tvTitle.setText(temp.nameProduct);
        tvInfo.setText(temp.info);
        tvPhone.setText(temp.phone);
        tvEmail.setText(temp.email);
       /*if(temp.getBitmap()!=null) {
            Bitmap bitmap = temp.getBitmap();

            ivProduct.setImageBitmap(bitmap);
        }*/
        if (temp.getId() != null && temp.getId().length() > 0)
            FirebaseStorage.getInstance().getReference().child(temp.getId()).getBytes(1024 * 1024 * 100).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    if (task.isSuccessful()) {
                        ivProduct.setImageBitmap(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                        ivProduct.setAdjustViewBounds(true);
                    } else {
                        Log.d("image failed", "image failed because: " + task.getException());
                    }
                }
            });

        return view;

    }
}
