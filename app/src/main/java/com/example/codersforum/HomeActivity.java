package com.example.codersforum;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.codersforum.R.id;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.codersforum.databinding.ActivityHomeBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;
//import com.example.codersforum.adaptors.TopicListAdaptor;
//import com.example.codersforum.datamodels.QuestionTitle;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding; // View binding

    private RecyclerView mTopicListView;
    private ExtendedFloatingActionButton mFab;
//    private TopicListAdaptor adaptor;
    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;
//    private List<QuestionTitle> questions;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private CircularImageView mProfileImg;
    private TextView mProfileName,mProfileEmail;
    private String name, userImg;
    private String username=" ";
    private DatabaseReference userRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
      //  getWindow().setStatusBarColor(Color.BLACK);
        init();

        mDrawer = findViewById(R.id.drawer_layout);

    }

    
    public void askQuestion () {
        Intent intent = new Intent(HomeActivity.this, DiscussionActivity.class);
        // intent.putExtra("IS_ASKING", true);
        startActivity(intent);
    }
    //    @Override
    protected void onStart() {
        super.onStart();
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " + item.getTitle(),Toast.LENGTH_SHORT).show();
        Intent intent = null;
//        int itemId = item.getItemId();
        int id = item.getItemId();
          if (id == R.id.signout_menu) {
            intent = new Intent(HomeActivity.this, AuthActivity.class);
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

              startActivity(intent);
            return true;
        }
        else if (id == R.id.about_menu) {
              intent = new Intent(HomeActivity.this, about.class);
              startActivity(intent);
        } else if (id == R.id.my_quiz_menu) {
            intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_discussions) {
            intent = new Intent(HomeActivity.this, DiscussionActivity.class);
            startActivity(intent);
        } else if (id == R.id.help) {
            // Handle the "Help" menu item

            return true;
        }
          else if (id == R.id.profile_menu) {
              intent = new Intent(HomeActivity.this, MyProfileActivity.class);
              startActivity(intent);
              return true;
          }
         else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }
//


    //
    private void init() {
//        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
//        name = sharedPreferences.getString("NAME", "");
//        username = sharedPreferences.getString("U_NAME", "");
//        userImg = sharedPreferences.getString("U_IMG", "https://images.pexels.com/photos/333850/pexels-photo-333850.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        mTopicListView = findViewById(R.id.topic_list_view);
//        mTopicListView.setLayoutManager(new LinearLayoutManager(this));
        mFab = findViewById(R.id.fab);
//        db = FirebaseFirestore.getInstance();
//        questions = new ArrayList<>();
//        adaptor = new TopicListAdaptor(questions, this);
//        mTopicListView.setAdapter(adaptor);
////        listenerRegistration = getListenerRegistration();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(HomeActivity.this, "Adding feature", Toast.LENGTH_SHORT).show();
                askQuestion();
//                Intent intent=new Intent(HomeActivity.this,DiscussionActivity.class);
//                startActivity(intent);
              //  return;
            }
        });
//
        mNavigationView = findViewById(R.id.navigation);
//
       mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
   @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        int itemId = item.getItemId();

        if (itemId == R.id.signout_menu)
        {
            Toast.makeText(HomeActivity.this, "Signing Out...", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        } else if (itemId == id.profile_menu) {
            intent = new Intent(HomeActivity.this, MyProfileActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.about_menu) {
            // Handle the "About" menu item
           // Toast.makeText(HomeActivity.this, "Function Under Process", Toast.LENGTH_SHORT).show();
            intent = new Intent(HomeActivity.this, about.class);
            startActivity(intent);

        } else if (itemId == id.my_quiz_menu) {
            intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (itemId == id.my_discussions) {
            intent = new Intent(HomeActivity.this, DiscussionActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.help) {
            // Handle the "Help" menu item
            Toast.makeText(HomeActivity.this, "Function Under Process", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
});


     
//
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    View headerLayout = mNavigationView.getHeaderView(0);
//        mProfileImg = headerLayout.findViewById(R.id.profile_img);
//
//        if (user.getPhotoUrl() != null) {
//            Log.d("USER_IN", "IMG: " + user.getPhotoUrl().toString());
//            Glide.with(this)
//                    .load(user.getPhotoUrl())
//                    .placeholder(R.drawable.app_logo)
//                    .into(mProfileImg);
//        }
//
        //Name
        mProfileName = headerLayout.findViewById(R.id.profile_name);
        mProfileEmail = (TextView)headerLayout.findViewById(id.email);
        if (user != null) {
            // User is signed in
            String userId = user.getUid();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Fetch user data from the Realtime Database
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User data found
                        RegModel userData = dataSnapshot.getValue(RegModel.class);
                        username = userData.getName();
                       // Toast.makeText(HomeActivity.this, "Username :"+username, Toast.LENGTH_SHORT).show();
                        mProfileName.setText(""+username);
                        mProfileEmail.setText(""+user.getEmail());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }

        mDrawer = findViewById(R.id.drawer_layout);

    }
}
