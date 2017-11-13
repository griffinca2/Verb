package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main3ActivityUserProf extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;


    //view objects
    private TextView userEmail;
    private Button logout;
    private Button discovery;
    //private ImageButton settings;
    private ImageButton settings;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //addListenerOnLogOut();

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();


        //initializing views
        userEmail = (TextView) findViewById(R.id.userEmail);
        name = (TextView) findViewById(R.id.displayName);
        logout = (Button) findViewById(R.id.buttonLogout);
        discovery = (Button) findViewById(R.id.discovery);
       // settings = (ImageButton) findViewById(R.id.buttonSettings);
        settings = (ImageButton) findViewById(R.id.buttonSettings);

        //if the user is not logged in
        //that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Main2ActivityLogin.class));

            // Showing toast message.
            Toast.makeText(Main3ActivityUserProf.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();

        //displaying logged in user name
        userEmail.setText(user.getEmail());
        name.setText(user.getDisplayName());


        //adding listener to button
        // Adding click listener on logout button.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Destroying login season.
                mAuth.signOut();

                // Finishing current User Profile activity.
                finish();

                // Redirect to Login Activity after click on logout button.
                Intent intent = new Intent(Main3ActivityUserProf.this, Main2ActivityLogin.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(Main3ActivityUserProf.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener to Sign up button.
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Main3ActivityUserProf.this, Main5ActivitySettings.class);
                startActivity(intent);

            }
        });
        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Main3ActivityUserProf.this, Main6ActivityDiscovery.class);
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
            startActivity(new Intent(this, Main2ActivityLogin.class));
        }
        if(view == settings){

            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Main5ActivitySettings.class));
        }
        if(view == discovery){

            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Main6ActivityDiscovery.class));
        }
    }
}
