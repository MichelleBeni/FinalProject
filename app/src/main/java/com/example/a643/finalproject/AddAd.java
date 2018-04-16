package com.example.a643.finalproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    String imageCode;






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
    {   UploadImage();
        Ad ad= new Ad(name.getText().toString(), info.getText().toString(), userEmail, phone,imageCode );
        adRef = database.getReference("AD").push();
        adRef.setValue(ad);
        Intent intent = new Intent(AddAd.this,MainScreen.class);
        startActivity(intent);
    }
    if(v==btnPhoto)
    {
        chooseImage();
    }
    }

    private void UploadImage()
    {
        if(filePath!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            imageCode = storageReference.getDownloadUrl().toString();

            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AddAd.this,"Uploaded ",Toast.LENGTH_SHORT).show();
                }

        }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddAd.this,"Not Uploaded "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int)progress+ "%" );
                }
            });

        }
    }
    private void chooseImage()
    {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                btnPhoto.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
