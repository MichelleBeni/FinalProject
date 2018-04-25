package com.example.a643.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class SearchAd extends AppCompatActivity implements View.OnClickListener {
    Button btnSearch;
    EditText searchKey;
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
        setContentView(R.layout.activity_search_ad);
        btnSearch = (Button)findViewById(R.id.searchAd);
        btnSearch.setOnClickListener(this);
        searchKey = (EditText) findViewById(R.id.searchKey);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Adref = database.getReference("AD");
        adsFind= new ArrayList<>();



        lv= (ListView)findViewById(R.id.lvSearch);


    }

    public void retrieveData() {
        Adref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    adsFind.clear();
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
                    if (Title.contains(Key)||Info.contains(Key) || Email.contains(Key)|| Phone.contains(Key)) {

                     adsFind.add(ads.get(i));
                    }


                }
                Log.d(adsFind.get(0).toString(), "onDataChange: ");
                allAdsAdapter = new AllAdsAdapter(SearchAd.this,0,0,adsFind);
                lv.setAdapter(allAdsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void searchAd() {
        Key = searchKey.getText().toString();

        retrieveData();




    }

    @Override
    public void onClick(View v) {
        if (v == btnSearch) {

            searchAd();




        }
    }
}
