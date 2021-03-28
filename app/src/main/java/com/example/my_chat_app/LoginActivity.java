package com.example.my_chat_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passowrdId);
        Button loginButton = findViewById(R.id.loginId);
        auth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String person_email = email.getText().toString();
                String person_password = password.getText().toString();

                if(TextUtils.isEmpty(person_email)||TextUtils.isEmpty(person_password))
                {
                    Toast.makeText(LoginActivity.this, "All fill doesn't fill up", Toast.LENGTH_SHORT).show();
                } else
                {
                   auth.signInWithEmailAndPassword(person_email,person_password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if(task.isSuccessful()){

                               Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent);
                               finish();
                           }
                           else{
                               Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
                }
            }
        });
    }
}