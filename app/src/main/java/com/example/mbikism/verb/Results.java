package com.example.mbikism.verb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Results extends AppCompatActivity{
    private Button profile;
    private Button discover;
    private TextView score;

    private FirebaseAuth mAuth;

    private DatabaseReference database;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        profile = (Button) findViewById(R.id.profile);
        discover = (Button) findViewById(R.id.discover);
        score = (TextView) findViewById(R.id.score);

        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(Results.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        score.setText(String.valueOf(Count.allCount));
        displayUpdates();



        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Results.this, ProfileVol.class);
                startActivity(intent);

            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Results.this, DiscoverPage.class);
                startActivity(intent);

            }
        });
    }

    public void displayUpdates(){
        FirebaseUser cuser = mAuth.getCurrentUser();
        if (cuser != null) {
            users.child(cuser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        User user1 = dataSnapshot.getValue(User.class);
                        if (user1 != null) {
                            score.setText(user1.quizScore + "");
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError firebaseError) {}
            });
        }
    }
}

