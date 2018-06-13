package com.example.a643.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
        etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
        etEmail=(EditText)findViewById(R.id.EmailSignUp);
        etEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        etPassword=(EditText)findViewById(R.id.PasswordSignUp);
        etType=(EditText)findViewById(R.id.type);



        etYears=(EditText)findViewById(R.id.years);
        FirebaseAuth = FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }


    public  void  register()
    {
        if(etPassword.getText().length()>=6&&etName.getText().length()>0&&(etPhone.getText().length()==10&&etPhone.getText().toString().startsWith("05"))&&(etEmail.getText().length()>0&&etEmail.getText().toString().contains("@"))) {
            etPassword.setError(null);
            etName.setError(null);
            etPhone.setError(null);
            etEmail.setError(null);
        FirebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUp_Activity.this,"Successfully registered", Toast.LENGTH_LONG).show();

                        if (Mover.isChecked()) {
                            UserMovers userMover = new UserMovers(etName.getText().toString(), etPhone.getText().toString(), etEmail.getText().toString(),
                                    etPassword.getText().toString(), etType.getText().toString(), etYears.getText().toString());
                            userRef = database.getReference("Users").push();
                            userRef.setValue(userMover);

                        } else {
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
        }else{
            if(etPassword.getText().length()<6){
                etPassword.setError("Password Length Must Be Greater Then 5");
            }
            if(etName.getText().length()==0){
                etName.setError("Name Cannot Be Empty");
            }
            if(etPhone.getText().length()==10){
                if(!etPhone.getText().toString().startsWith("05")){
                    etPhone.setError("Invalid Phone Number");
                }
            }else{
                if(!etPhone.getText().toString().startsWith("05")){
                    etPhone.setError("Invalid Phone Number, Length Must Be 9");
                }else{
                    etPhone.setError("Length Must Be 9");
                }
            }
            if(!etEmail.getText().toString().contains("@")){
                etEmail.setError("Invalid Email");
            }else{
                if(etEmail.getText().length()==0){
                    etEmail.setError("Cant Be Empty");

                }
            }
        }

    }
    @Override
    public void onClick(View v) {
        if(v==btnSignUp) {
            register();
        }
    }
}
