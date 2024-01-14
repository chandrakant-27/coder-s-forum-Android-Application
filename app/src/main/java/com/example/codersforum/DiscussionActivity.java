package com.example.codersforum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DiscussionActivity extends AppCompatActivity {

    // DiscussionActivity.java
    private DatabaseReference discussionRef;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private DatabaseReference userRef;
    String username;
    private DrawerLayout mDrawer;

    TextView userTextView,contentTextView;
    FirebaseListAdapter<Message> adapter;

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_discussion);
            getWindow().setStatusBarColor(Color.BLACK);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            String discussionId = "general"; // You can have different discussion rooms
            discussionRef = FirebaseDatabase.getInstance().getReference().child("discussions").child(discussionId).child("messages");

            displayMessages();

            EditText messageEditText = findViewById(R.id.messageEditText);
            ImageView sendButton = findViewById(R.id.sendButton);

            sendButton.setOnClickListener(view -> {
                String messageText = messageEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(messageText)) {
                    sendMessage(messageText);
                    messageEditText.setText("");
                }
            });



            mDrawer = findViewById(R.id.drawer_layout);

//    }
//

        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.stopListening();
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
            intent = new Intent(DiscussionActivity.this, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            return true;
        }
        else if (id == R.id.about_menu) {
            intent = new Intent(DiscussionActivity.this, about.class);
            startActivity(intent);
        } else if (id == R.id.my_quiz_menu) {
            intent = new Intent(DiscussionActivity.this, WelcomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_discussions) {
            intent = new Intent(DiscussionActivity.this, DiscussionActivity.class);
            startActivity(intent);
        } else if (id == R.id.profile_menu) {
            intent = new Intent(DiscussionActivity.this, MyProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void displayMessages() {
        ListView messageListView = findViewById(R.id.messageListView);

        FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                .setLayout(R.layout.message_item)
                .setQuery(discussionRef, Message.class)
                .build();

         adapter = new FirebaseListAdapter<Message>(options) {
            @Override
            protected void populateView(View view, Message message, int position) {
                 userTextView = view.findViewById(R.id.userTextView);
                 contentTextView = view.findViewById(R.id.contentTextView);

                // Check if the message is within the last N minutes (adjust the time window as needed)
                if (isWithinTimeWindow(message.getTimestamp(), 10000)) {
                    userTextView.setText(message.getUser());
                    contentTextView.setText(message.getContent());
                } else {
                    // Optionally, you can hide or remove views for messages outside the time window
                    userTextView.setVisibility(View.GONE);
                    contentTextView.setVisibility(View.GONE);
                }
            }
         };
        messageListView.setAdapter(adapter);

        // Call startListening to begin observing the database changes
        adapter.startListening();
    }
    private boolean isWithinTimeWindow(long messageTimestamp, int minutes) {
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - messageTimestamp;
        long minutesDifference = timeDifference / (60 * 1000); // Convert milliseconds to minutes

        return minutesDifference <= minutes;
    }

    private void sendMessage(String messageText) {


        user = FirebaseAuth.getInstance().getCurrentUser();
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

                        long timestamp = System.currentTimeMillis();
                        Message message = new Message(username, messageText, timestamp);
                        discussionRef.push().setValue(message);
                    } else {

                        // User data not found
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        } else {
            // No user is signed in
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
        }


        }
    }
