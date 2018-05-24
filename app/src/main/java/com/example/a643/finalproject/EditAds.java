package com.example.a643.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditAds extends AppCompatActivity {
    String Key;
    FirebaseAuth firebaseAuth;
    DatabaseReference Adref;
    ArrayList<Ad> ads;
    ArrayList<Ad> adsFind;


    AllAdsAdapter allAdsAdapter;
    ListView lv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ads);
       FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Key= firebaseUser.getEmail().toString();
        Toast.makeText(EditAds.this,Key,Toast.LENGTH_LONG);
        Adref = database.getReference("AD");
        adsFind= new ArrayList<>();
        lv= (ListView)findViewById(R.id.lvMyAds);
        retrieveData();



    }

    public void retrieveData() {
        Adref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ads = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Ad ad = data.getValue(Ad.class);
                    ad.setId(data.getKey());
                    ads.add(ad);

                }
                for (int i = 0; i < ads.size(); i++) {
                    String Title = ads.get(i).getNameProduct();
                    String Info = ads.get(i).getInfo();
                    String Email = ads.get(i).getEmail();
                    String Phone= ads.get(i).getPhone();
                    if (Email.equals(Key)) {

                        adsFind.add(ads.get(i));
                    }


                }
                Log.d(adsFind.get(0).toString(), "onDataChange: ");
                allAdsAdapter = new AllAdsAdapter(EditAds.this,0,0,adsFind);
                lv.setAdapter(allAdsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
