package com.example.a643.finalproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
    FirebaseAuth firebaseAuth;

    DatabaseReference userRef;
    String userEmail;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        tvName = findViewById(R.id.Myname);
        tvPhone = findViewById(R.id.MyPhone);
        tvEmail = findViewById(R.id.MyEmail);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();

        userEmail = firebaseUser.getEmail();
        tvEmail.setText(userEmail);
        userRef = database.getReference("Users");
        retrieveData();
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
                        tvName.setText(users.get(i).getName());
                        tvPhone.setText(users.get(i).getPhone());

                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
