package com.example.a643.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;
    Context context;
    FirebaseAuth firebaseAuth;
    User user;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String key;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        tvName = (TextView)findViewById(R.id.Myname);
        tvPhone = (TextView)findViewById(R.id.MyPhone);
        tvEmail= (TextView)findViewById(R.id.MyEmail);
        database= FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        FirebaseUser firebaseUser= firebaseAuth.getInstance().getCurrentUser();

        key = "-L1CwxRqYP-AODVanZ-T";
        userRef = database.getReference("Users/" + key);
        this.retrieveData();


    }
    public void retrieveData()
    {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                tvName.setText(user.name);
                tvPhone.setText(user.phone);
                tvEmail.setText(user.email);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
