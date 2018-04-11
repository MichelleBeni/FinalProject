package com.example.a643.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import dmax.dialog.SpotsDialog;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    TextView SignOut;
    Intent intent;
    Context context;
    FirebaseAuth firebaseAuth;
    Button search;
    Button addAd;
    ImageView myProfile;
    ListView lv;
    ArrayList<Ad> ads;
    private android.app.AlertDialog loading;
    AllAdsAdapter allAdsAdapter;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        database= FirebaseDatabase.getInstance().getReference("AD");
        lv= (ListView)findViewById(R.id.lv);

      this.retriveData();



        myProfile = (ImageView) findViewById(R.id.myProfile);
        myProfile.setOnClickListener(this);
        SignOut= (TextView)findViewById(R.id.SignOut);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        SignOut.setOnClickListener(this);
        search= (Button) findViewById(R.id.search_ad);
        search.setOnClickListener(this);
        context= this;
        addAd=(Button)findViewById(R.id.AddAd);
        addAd.setOnClickListener(this);
    }

    public void retriveData()
    {
        loading= new SpotsDialog(MainScreen.this,R.style.Custom);
        loading.show();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ads = new ArrayList<Ad>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Ad ad = data.getValue(Ad.class);
                    ads.add(ad);

                }
                allAdsAdapter = new AllAdsAdapter(MainScreen.this, 0, 0, ads);
                lv.setAdapter(allAdsAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    public void onClick(View v) {

       if(v==SignOut) {
           firebaseAuth.signOut();
           intent = new Intent(context, Enter_Activity.class);
           startActivity(intent);
       }
       if(v==search)
       {
           intent = new Intent(context,SearchAd.class);
           startActivity(intent);
       }
       if(v==addAd)
       {
           intent = new Intent(context,AddAd.class);
           startActivity(intent);
       }
       if(v==myProfile)
       {
           intent = new Intent(context,MyProfile.class);
           startActivity(intent);
       }

    }
}
