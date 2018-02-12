package com.example.a643.finalproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Enter_Activity extends AppCompatActivity implements View.OnClickListener {
    Button btnLog;
    Button btnReg;
    TextView tvReg;
    EditText edEmail;
    EditText edPassword;
    Dialog d;
    FirebaseAuth firebaseAuth;
    boolean succes;
    int mode=0; //0 register, 1 login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_);

        btnLog= (Button)findViewById(R.id.LoginButton);
        btnLog.setOnClickListener(this);

        tvReg=(TextView) findViewById(R.id.SignUPButton);
        tvReg.setOnClickListener(this);

        edEmail=(EditText)findViewById(R.id.EmailInput);
        edPassword=(EditText)findViewById(R.id.PasswordInput);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent intent = new Intent(Enter_Activity.this, MainScreen.class);
            startActivity(intent);
            finish();
        }


    }



        public void Login(  )
        {

            FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
            if(firebaseUser!=null)
            {
                firebaseAuth.signOut();
            }

            firebaseAuth.signInWithEmailAndPassword(edEmail.getText().toString(),edPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(Enter_Activity.this, MainScreen.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Enter_Activity.this, "Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
        }

    @Override
    public void onClick(View v) {
        if(v==tvReg)
        {
            Intent intent = new Intent(Enter_Activity.this,SignUp_Activity.class);
            startActivity(intent);
        }
        if(v==btnLog)
        {

            Login();


        }
    }
}
