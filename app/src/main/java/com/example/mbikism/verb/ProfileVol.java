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

public class ProfileVol extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;

    //view objects
    private TextView userEmail;
    private Button logout;
    private Button discovery;
    private Button retakeQuiz;
    private ImageButton settings;
    private TextView name;
    private TextView birthday;

    private DatabaseReference database;
    private DatabaseReference users;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_vol);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        userEmail = (TextView) findViewById(R.id.userEmail);
        name = (TextView) findViewById(R.id.displayName);
        logout = (Button) findViewById(R.id.buttonLogout);
        discovery = (Button) findViewById(R.id.discovery);
        retakeQuiz = (Button) findViewById(R.id.buttonRetake);
        settings = (ImageButton) findViewById(R.id.buttonSettings);
        birthday = (TextView) findViewById(R.id.displayBirthday);

        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        //fuser = mAuth.getCurrentUser();

        //if the user is not logged in that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(ProfileVol.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(ProfileVol.this, Login.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(ProfileVol.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener to Settings button.
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileVol.this, SettingsVol.class);
                startActivity(intent);
            }
        });
        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileVol.this, DiscoverPage.class);
                startActivity(intent);

            }
        });
        retakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileVol.this, q1.class);
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
            startActivity(new Intent(this, SettingsVol.class));
        }
        if(view == discovery){
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, DiscoverPage.class));
        }
    }

    public void displayUpdates(FirebaseUser u){
        if(u != null) {
            //user = mAuth.getCurrentUser();
            userEmail.setText(u.getEmail());
            //name.setText(user.getDisplayName());
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user1 = dataSnapshot.getValue(User.class);
                    if (user1 == null) {
                        Toast.makeText(ProfileVol.this, "User is unexpectedly null.", Toast.LENGTH_LONG).show();
                    } else {
                        birthday.setText(user1.birthday);
                        name.setText(user1.firstName + " " + user1.lastName);
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                }
            };
            //users.child("volunteers").child(u.getUid()).addListenerForSingleValueEvent(eventListener);
            users.child(u.getUid()).addListenerForSingleValueEvent(eventListener);
        }

    }

}
