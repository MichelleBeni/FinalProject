package com.example.a643.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp_Activity extends AppCompatActivity implements View.OnClickListener {
    EditText etName;
    EditText etPhone;
    EditText etEmail;
    EditText etPassword;
    EditText etType;
    EditText etYears;
    Button btnSignUp;
    FirebaseAuth FirebaseAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    CheckBox Mover;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_);

        btnSignUp= (Button)findViewById(R.id.SignUp);
        btnSignUp.setOnClickListener(this);
        Mover= (CheckBox)findViewById(R.id.ifMover);
        etName=(EditText)findViewById(R.id.name);
        etPhone=(EditText)findViewById(R.id.Phone);
        etEmail=(EditText)findViewById(R.id.EmailSignUp);
        etPassword=(EditText)findViewById(R.id.PasswordSignUp);
        etType=(EditText)findViewById(R.id.type);
        etYears=(EditText)findViewById(R.id.years);
        FirebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }


    public  void  register()
    {
        FirebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUp_Activity.this,"Successfully registered", Toast.LENGTH_LONG).show();
                    if(Mover.isChecked())
                    {
                        UserMovers userMover = new UserMovers(etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(),
                                etPassword.getText().toString(),etType.getText().toString(),etYears.getText().toString());
                        userRef= database.getReference("Users").push();
                        userRef.setValue(userMover);
                    }
                    else {
                        User user = new User(etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                        userRef = database.getReference("Users").push();
                        userRef.setValue(user);
                    }
                    Intent intent = new Intent(SignUp_Activity.this,Enter_Activity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SignUp_Activity.this, "Registration Error", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v==btnSignUp) {
            register();
        }
    }
}
