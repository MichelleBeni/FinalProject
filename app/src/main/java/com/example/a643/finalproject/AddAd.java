package com.example.a643.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddAd extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth FirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference adRef;
    EditText name;
    EditText info;
    Button add;
    String userEmail;
    DatabaseReference userRef;
    ArrayList<User> users;
    String phone;



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
        userEmail=firebaseUser.getEmail();
        userRef = database.getReference("Users");
        this.retrieveData();


    }
    public void retrieveData() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users = new ArrayList<>();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    users.add(user);

                }

                for (int i = 0; i < users.size(); i++) {
                    String mail = users.get(i).getEmail();
                    if (mail.equalsIgnoreCase(userEmail)) {

                        phone=users.get(i).getPhone().toString();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    @Override
    public void onClick(View v) {
    if(v==add)
    {
        Ad ad= new Ad(name.getText().toString(), info.getText().toString(), userEmail, phone );
        adRef = database.getReference("AD").push();
        adRef.setValue(ad);
        Intent intent = new Intent(AddAd.this,MainScreen.class);
        startActivity(intent);
    }

    }
}
