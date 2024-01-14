package com.example.codersforum;
// WelcomeActivity.java
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setStatusBarColor(Color.BLACK);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;

    }
    public void javaQuiz(View view)
    {
        Intent intent = new Intent(WelcomeActivity.this, JavaQuizActivity.class);
        startActivity(intent);
    }
    public void pythonQuiz(View view)
    {
        Intent intent = new Intent(WelcomeActivity.this, PythonQuizActivity.class);
        startActivity(intent);
    }
    public void CQuiz(View view)
    {
        Intent intent = new Intent(WelcomeActivity.this, CQuizActivity.class);
        startActivity(intent);
    }
    public void CppQuiz(View view)
    {
        Intent intent = new Intent(WelcomeActivity.this, CppQuizActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " + item.getTitle(),Toast.LENGTH_SHORT).show();
        Intent intent = null;
//        int itemId = item.getItemId();
        int id = item.getItemId();
        if (id == R.id.signout_menu) {
            intent = new Intent(WelcomeActivity.this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            return true;
        }
        else if (id == R.id.about_menu) {
            intent = new Intent(WelcomeActivity.this, about.class);
            startActivity(intent);
        } else if (id == R.id.my_quiz_menu) {
            intent = new Intent(WelcomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_discussions) {
            intent = new Intent(WelcomeActivity.this, DiscussionActivity.class);
            startActivity(intent);
        } else if (id == R.id.profile_menu)
        {
            intent = new Intent(WelcomeActivity.this, MyProfileActivity.class);
            startActivity(intent);

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
