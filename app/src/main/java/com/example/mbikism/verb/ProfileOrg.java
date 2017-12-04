package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileOrg extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    //view objects
    private TextView userEmail;
    private Button logout;
    private Button discovery;
    private Button retakeQuiz;
    private ImageButton settings;
    private TextView name;
    private TextView aboutView;
    private String about;

    private DatabaseReference database;
    private DatabaseReference users;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_org);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        userEmail = (TextView) findViewById(R.id.userEmail);
        name = (TextView) findViewById(R.id.orgName);
        logout = (Button) findViewById(R.id.buttonLogout);
        discovery = (Button) findViewById(R.id.discovery);
        retakeQuiz = (Button) findViewById(R.id.buttonRetake);
        settings = (ImageButton) findViewById(R.id.buttonSettings);
        aboutView = (TextView) findViewById(R.id.about);

        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");


        //if the user is not logged in that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(ProfileOrg.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        user = mAuth.getCurrentUser();

        //display current information
        displayUpdates(user);

        // Adding click listener on logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Destroying login season.
                mAuth.signOut();

                // Finishing current User Profile activity.
                finish();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(ProfileOrg.this, Login.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(ProfileOrg.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener to Settings button.
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileOrg.this, SettingsOrg.class);
                startActivity(intent);
            }
        });
        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileOrg.this, DiscoverPageOrg.class);
                startActivity(intent);

            }
        });
        retakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileOrg.this, nq1.class);
                startActivity(intent);

            }
        });
    }

    public void onClick(View view) {
        //if logout is pressed
        if(view == logout){
            //logging out the user
            mAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));
        }
        if(view == settings){

            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SettingsOrg.class));
        }
        if(view == discovery){

            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, DiscoverPageOrg.class));
        }
    }

    public void displayUpdates(FirebaseUser u){
        user = mAuth.getCurrentUser();
        userEmail.setText(u.getEmail());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                if (user1 == null) {
                    Toast.makeText(ProfileOrg.this, "User is unexpectedly null.", Toast.LENGTH_LONG).show();
                } else {
                    aboutView.setText(user1.about);
                    name.setText(user1.orgName);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {}
        };
        //users.child("organizations").child(u.getUid()).addListenerForSingleValueEvent(eventListener);
        users.child(u.getUid()).addListenerForSingleValueEvent(eventListener);
    }
}
