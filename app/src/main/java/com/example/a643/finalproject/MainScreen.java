package com.example.a643.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreen extends AppCompatActivity {
  TextView SignOut;
    Intent intent;
    Context context;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        SignOut= (TextView)findViewById(R.id.SignOut);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        SignOut.setOnClickListener(this);
        context= this;
    }

    @Override
    public void onClick(View v) {

        firebaseAuth.signOut();
        intent = new Intent(context,Enter_Activity.class);
        startActivity(intent);

    }
}
