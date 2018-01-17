package com.example.a643.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {
    TextView SignOut;
    Intent intent;
    Context context;
    FirebaseAuth firebaseAuth;
    Button search;
    Button addAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
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

    }
}
