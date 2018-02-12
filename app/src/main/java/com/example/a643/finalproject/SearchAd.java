package com.example.a643.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchAd extends AppCompatActivity implements View.OnClickListener {
    Button btnSearch;
    EditText searchKey;
    String Key;
    FirebaseAuth firebaseAuth;
    DatabaseReference Adref;
    ArrayList<Ad> ads;

    TextView title;
    TextView info;
    TextView email;
    TextView phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ad);
        btnSearch = findViewById(R.id.searchAd);
        btnSearch.setOnClickListener(this);
        searchKey = findViewById(R.id.searchKey);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Adref = database.getReference("AD");

        title = findViewById(R.id.SearchTitle);
        info = findViewById(R.id.SearchInfo);
        email = findViewById(R.id.SearchEEmail);
        phone = findViewById(R.id.SearchPhone);


    }

    public void retrieveData() {
        Adref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ads = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Ad ad = data.getValue(Ad.class);
                    ads.add(ad);

                }
                for (int i = 0; i < ads.size(); i++) {
                    String Title = ads.get(i).getNameProduct();
                    String Info = ads.get(i).getInfo();
                    String Email = ads.get(i).getEmail();
                    String Phone= ads.get(i).getPhone();
                    if (Title.contains(Key)||Info.contains(Key) || Email.contains(Key)|| Phone.contains(Key)) {

                      title.setText(Title);
                        info.setText(Info);
                        email.setText(Email);
                        phone.setText(Phone);
                    }


                }
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
