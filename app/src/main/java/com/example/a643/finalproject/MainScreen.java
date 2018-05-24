package com.example.a643.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class MainScreen extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    Context context;
    FirebaseAuth firebaseAuth;
    ListView lv;
    ArrayList<Ad> ads;
    private android.app.AlertDialog loading;
    AllAdsAdapter allAdsAdapter;
    private DatabaseReference database;
    ImageView adPic;
    String imageCode;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    StorageReference imageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        database= FirebaseDatabase.getInstance().getReference("AD");
        lv= (ListView)findViewById(R.id.lv);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);





      this.retriveData();




        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();

        context= this;
      /*  addAd=(Button)findViewById(R.id.AddAd);
        addAd.setOnClickListener(this);*/
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
                    try {
                        Ad ad = data.getValue(Ad.class);
                        ad.setId(data.getKey());
                        ads.add(ad);
                    }catch(Exception ignored){

                    }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.searchAd:

                intent = new Intent(context,SearchAd.class);
                startActivity(intent);
                return true;

            case  R.id.signOut:
                firebaseAuth.signOut();
                intent = new Intent(context, Enter_Activity.class);
                startActivity(intent);
                return true;

            case  R.id.addNewAd:
                intent = new Intent(context,AddAd.class);
                startActivity(intent);
                return  true;

            case  R.id.editAd:
                intent = new Intent(context,EditAds.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View v) {





    }
}
