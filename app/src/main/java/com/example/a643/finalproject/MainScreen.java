package com.example.a643.finalproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.net.URI;
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
    Ad lastSelected;
    String KEY;
    FirebaseUser firebaseUser;

     DatabaseReference databaseUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ArrayList<UserMovers> users;
     StorageReference imageRef;
    String userEmail;
    String phone;
    String Key;
    boolean Mover = false;
    private boolean success=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        database= FirebaseDatabase.getInstance().getReference("AD");
        lv= (ListView)findViewById(R.id.lv);
        databaseUser= FirebaseDatabase.getInstance().getReference("Users");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        this.retriveData();
        firebaseAuth = FirebaseAuth.getInstance();
       firebaseUser = firebaseAuth.getCurrentUser();
        userEmail = firebaseUser.getEmail();
        this.retrieveDataUser();



        context= this;

        checkSystemWritePermission();

    }


    //making the info about the user
    public void retrieveDataUser() {
       databaseUser.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               users = new ArrayList<UserMovers>();

               for (DataSnapshot data : dataSnapshot.getChildren()) {
                   UserMovers user = data.getValue(UserMovers.class);
                   users.add(user);

               }

               for (int i = 0; i < users.size(); i++) {
                   String mail = users.get(i).getEmail();
                   if (mail.equalsIgnoreCase(userEmail.toString())) {
                       phone=users.get(i).getPhone();

                       if(users.get(i).getLicenseType()!=null||users.get(i).getYearsDrive()!=null)
                       {
                           Mover=true;
                       }

                   }


               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }

    //making info about the ads
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
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        lastSelected = ads.get(position);
                        KEY= lastSelected.getId();
                        if(lastSelected.getEmail().equals(firebaseUser.getEmail().toString())) {
                            showUpdateDialog(lastSelected.getNameProduct(), lastSelected.getInfo());
                        }


                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    // Updating ad
    private void showUpdateDialog(String name, String info)
    {
        AlertDialog.Builder dialogBuilder= new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView= inflater.inflate(R.layout.updatedialog,null);

        dialogBuilder.setView(dialogView);

        final EditText EditTitle = (EditText)dialogView.findViewById(R.id.changeTitle);
        final EditText EditInfo= (EditText)dialogView.findViewById(R.id.changeInfo);
        final Button  btnUpdate= (Button)dialogView.findViewById(R.id.update);
        final Button btnDelete= (Button)dialogView.findViewById(R.id.Delete);
        final AlertDialog alertDialog= dialogBuilder.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAd(EditTitle.getText().toString(),EditInfo.getText().toString());
                alertDialog.dismiss();


            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAd();
                alertDialog.dismiss();
            }

        });



    }

    private boolean updateAd(String title, String info)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AD").child(KEY);
        Ad ad = new Ad(title,info,userEmail,phone);
        databaseReference.setValue(ad);
        Toast.makeText(this,"Ad updated",Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteAd()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("AD").child(KEY);

        databaseReference.removeValue();
        Toast.makeText(this,"Ad Deleted",Toast.LENGTH_LONG).show();
        return true;
    }


    //making menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                intent=new Intent(context,About.class);
                startActivity(intent);
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
               if(Mover)
                {
                    intent = new Intent(context,AddAdMover.class);
                    startActivity(intent);
                    return  true;
                }
                else {
                    intent = new Intent(context, AddAd.class);
                    startActivity(intent);
                    return true;
                }



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }




    private void checkSystemWritePermission() {
        Boolean value;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(this);
            if (value) {
                success = true;
                startService(new Intent(this, BatteryService.class));
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 1000);
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Boolean value = Settings.System.canWrite(this);
                if (value) {
                    success = true;
                    startService(new Intent(this, BatteryService.class));
                }
            }
        }
    }


    @Override
    public void onClick(View v) {





    }
}
