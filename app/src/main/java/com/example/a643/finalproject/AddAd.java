package com.example.a643.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
    ImageView btnPhoto;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST= 71;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        FirebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();

        add= (Button)findViewById(R.id.addNewAd);
        add.setOnClickListener(this);
        name = (EditText)findViewById(R.id.product);
        name.setOnClickListener(this);
        info = (EditText)findViewById(R.id.info);
        info.setOnClickListener(this);
        FirebaseUser firebaseUser= FirebaseAuth.getCurrentUser();
        userEmail=firebaseUser.getEmail();
        userRef = database.getReference("Users");

        btnPhoto=(ImageView)findViewById(R.id.imageViewAd);
        btnPhoto.setOnClickListener(this);


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

        Ad ad= new Ad(name.getText().toString(), info.getText().toString(), userEmail, phone);
        adRef = database.getReference("AD").push();
        adRef.setValue(ad);
        String key = adRef.getKey();
        byte[] imageBytes = ImageTools.getBytesFromBitmap(ImageTools.getBitmapFromImage(btnPhoto));
        FirebaseStorage.getInstance().getReference().child(key).putBytes(imageBytes).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AddAd.this, "success to upload image", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(AddAd.this, "failed to upload image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    if(v==btnPhoto)
    {
        chooseImage();
    }
    }


    private void chooseImage()
    {

        Intent chooseImageIntent = ImageTools.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK)
        {
            Bitmap bitmap = ImageTools.getImageFromResult(this,  resultCode, data);
            bitmap = ImageTools.scaleBitmap(bitmap, 600, 600);
            btnPhoto.setImageBitmap(bitmap);
        }
    }
}
