package com.example.mbikism.verb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiscoverPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private TextView shownps;

    private DatabaseReference database;
    private DatabaseReference users;
    //view objects
    private Button userProfile;
    CoordinatorLayout cl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_page);
        shownps = (TextView) findViewById(R.id.showNP);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();
        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");


        //initializing views
        userProfile = (Button) findViewById(R.id.buttonProfile);

        cl = (CoordinatorLayout)findViewById(R.id.cl);

        //if the user is not logged in
        //that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(DiscoverPage.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();

        getScore();

        //adding listener to button
        // Adding click listener on logout button.
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finishing current User Profile activity.
                finish();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(DiscoverPage.this, ProfileVol.class);
                startActivity(intent);
            }
        });
    }

    public void getScore(){
        shownps.setText("TestOrg\n" + "TestOrg2\n");

        FirebaseUser u = mAuth.getCurrentUser();
        if(u != null) {
            //user = mAuth.getCurrentUser();
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user1 = dataSnapshot.getValue(User.class);
                    if (user1 == null) {
                        Toast.makeText(DiscoverPage.this, "User is unexpectedly null.", Toast.LENGTH_LONG).show();
                    } else {
                        int volQuizScore = user1.quizScore;
                        //compare(volQuizScore);
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                }
            };
            users.child("volunteers").child(u.getUid()).addListenerForSingleValueEvent(eventListener);
        }

    }
    public void compare(final int volScore) {
        FirebaseDatabase.getInstance().getReference().child("users").child("organizations")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Toast.makeText(DiscoverPage.this, "NP", Toast.LENGTH_LONG).show();
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            int npScore = user.quizScore;
                            //Toast.makeText(DiscoverPage.this, npScore + "", Toast.LENGTH_LONG).show();
                            int max = volScore + 5;
                            int min = volScore - 5;
                            int s = npScore * 2;
                            i += 1;
                            if(s <= max && s >= min){
                                String orgName = user.orgName;
                                //createTextView(i, orgName);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void createTextView(int i, String orgName){
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        tv.setText(orgName);
        tv.setId(i + 5);
        cl.addView(tv);
    }

    public void onClick(View view) {
        //if logout is pressed
        if(view == userProfile){
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileVol.class));
        }

    }
}