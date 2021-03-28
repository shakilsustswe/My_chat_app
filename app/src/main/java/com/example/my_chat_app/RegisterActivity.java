package com.example.my_chat_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
      ///show action bar
        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username = findViewById(R.id.usernameId);
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passowrdId);
        Button registerButton = findViewById(R.id.registerId);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String person_name = username.getText().toString();
                String person_email = email.getText().toString();
                String person_password = password.getText().toString();

                if(TextUtils.isEmpty(person_name)||TextUtils.isEmpty(person_email)||TextUtils.isEmpty(person_password))
                {
                    Toast.makeText(RegisterActivity.this, "All fill doesn't fill up", Toast.LENGTH_SHORT).show();
                }
                else if(person_password.length()<6)
                {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 charecters", Toast.LENGTH_SHORT).show();
                }else
                {
                    register(person_name,person_email,person_password);
                }
            }
        });
    }


    private void register(final String username, String email, String password)
    {
       auth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful())
                       {
                           Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                           FirebaseUser firebaseUser = auth.getCurrentUser();

                          //assert firebaseUser != null;
                           String userid = firebaseUser.getUid();

                           reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                           HashMap<String,String>hashMap = new HashMap<>();
                           hashMap.put("id",userid);
                           hashMap.put("username",username);
                           hashMap.put("imageURL","default");

                           reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                           {

                               @Override
                               public void onComplete(@NonNull Task<Void> task) {

                                   if(task.isSuccessful())
                                   {
                                       Toast.makeText(RegisterActivity.this, "shakil", Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                       startActivity(intent);
                                       finish();
                                   }
                               }
                           });
                       }
                       else
                       {
                           Toast.makeText(RegisterActivity.this, "You can't register worth this email or password", Toast.LENGTH_SHORT).show();
                       }
                   }

               });

    }
}