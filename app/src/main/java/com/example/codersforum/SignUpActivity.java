package com.example.codersforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private MaterialCheckBox mCheckBox;
    private MaterialButton mSignup;
    private TextView mNotfiyTv;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mydb=FirebaseDatabase.getInstance().getReference().child("users");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setStatusBarColor(Color.BLACK);
        mName = findViewById(R.id.profile_name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password_et);
        mCheckBox = findViewById(R.id.checkBox);
        mSignup = findViewById(R.id.signup_button);
        mNotfiyTv = findViewById(R.id.notify_user_tv);
        mToolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();

        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finishAfterTransition();
        return false;
    }

    private void init() {
       // setSupportActionBar(mToolbar);
        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        if (mName.getText().toString().length() < 3) {
            mNotfiyTv.setText("Name must be at least 3 characters");
            mNotfiyTv.setVisibility(View.VISIBLE);
            Toast.makeText(this , "Name must be at least 3 characters", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!isValidEmail(mEmail.getText().toString())) {
            mNotfiyTv.setText("Email address is invalid.");
            mNotfiyTv.setVisibility(View.VISIBLE);
            Toast.makeText(this , "Email address is invalid.", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
            mNotfiyTv.setText("Password must match confirm password.");
            mNotfiyTv.setVisibility(View.VISIBLE);
            Toast.makeText(this , "Password must match confirm password.", Toast.LENGTH_SHORT).show();


            return;
        }

        if (!mCheckBox.isChecked()) {
            mNotfiyTv.setText("Please check the agreement policy.");
            mNotfiyTv.setVisibility(View.VISIBLE);
            Toast.makeText(this , "Please check the agreement policy.", Toast.LENGTH_SHORT).show();
            return;
        }

        mNotfiyTv.setVisibility(View.GONE);
        signUpWithEmailPassword(mEmail.getText().toString(), mPassword.getText().toString());
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void signUpWithEmailPassword(String email, String password)
    {
        String name=mName.getText().toString();

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
//                            Toast.makeText(SignUpActivity.this, "UID :"+userId, Toast.LENGTH_SHORT).show();


                            RegModel reg=new RegModel(email,name,password);
                            mydb.child(userId).setValue(reg).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    Toast.makeText(SignUpActivity.this, "Record inserted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this,AuthActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(SignUpActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this,"Error: " + task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    }
   }
                );



    }
    public void redirectToLogin(View view)
    {
        Intent intent = new Intent(SignUpActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}

