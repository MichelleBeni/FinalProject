package com.example.a643.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddAdMover extends AppCompatActivity implements View.OnClickListener {
    com.google.firebase.auth.FirebaseAuth FirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference adRef;
    EditText name;
    EditText info;
    Button add;
    String userEmail;
    DatabaseReference userRef;
    ArrayList<UserMovers> users;
    String phone;
    String type;
    String yearsDriving;
    ImageView btnPhoto;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST= 71;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad_mover);

        FirebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();



        add= (Button)findViewById(R.id.addNewMovingAd);
        add.setOnClickListener(this);
        name = (EditText)findViewById(R.id.DateType);
        name.setOnClickListener(this);
        info = (EditText)findViewById(R.id.infoMoving);
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
                    UserMovers user = data.getValue(UserMovers.class);
                    users.add(user);

                }

                for (int i = 0; i < users.size(); i++) {
                    String mail = users.get(i).getEmail();
                    if (mail.equalsIgnoreCase(userEmail)) {

                        phone=users.get(i).getPhone().toString();
                        type = users.get(i).getLicenseType().toString();
                        yearsDriving = users.get(i).getYearsDrive().toString();

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

            Ad ad= new Ad(name.getText().toString(), info.getText().toString()+" type of license: "+ type +", years driving: "+ yearsDriving+" ", userEmail, phone);
            adRef = database.getReference("AD").push();
            adRef.setValue(ad);
            String key = adRef.getKey();
           finish();


        }

    }
}
