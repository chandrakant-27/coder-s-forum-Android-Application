package com.example.codersforum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
    }
    protected  void BackToHomePage(View view)
    {
        Intent i=new Intent(MyProfileActivity.this,HomeActivity.class);
        startActivity(i);
    }
}