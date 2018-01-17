package com.example.a643.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAd extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth FirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference adRef;
    EditText name;
    EditText info;
    Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);
        FirebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        add= (Button)findViewById(R.id.addNewAd);
        add.setOnClickListener(this);
        name = (EditText)findViewById(R.id.product);
        name.setOnClickListener(this);
        info = (EditText)findViewById(R.id.info);
        info.setOnClickListener(this);
       FirebaseUser firebaseUser= FirebaseAuth.getCurrentUser();

    }

    @Override
    public void onClick(View v) {
    if(v==add)
    {
        Ad ad= new Ad(name.getText().toString(), info.getText().toString(), "Jake", "0508655654" );
        adRef = database.getReference("AD").push();
        adRef.setValue(ad);
        Intent intent = new Intent(AddAd.this,MainScreen.class);
        startActivity(intent);
    }

    }
}
