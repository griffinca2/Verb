package com.example.mbikism.verb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class OrgName extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;


    //view objects
    private Button cont;
    private Button back;
    private TextView name;

    private DatabaseReference database;
    private DatabaseReference users;

    private FirebaseUser user;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_name);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();


        //initializing views
        name = (TextView) findViewById(R.id.name);
        cont= (Button) findViewById(R.id.buttonCont);
        back = (Button) findViewById(R.id.buttonBack);


        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
            Toast.makeText(OrgName.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        user = mAuth.getCurrentUser();

        //adding listener to button
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Destroying login season.
                mAuth.signOut();
                // Finishing current User Profile activity.
                finish();
                // Redirect to Login Activity after click on logout button.
                setName();
                Intent intent = new Intent(OrgName.this, ProfileOrg.class);
                startActivity(intent);
            }
        });

        // Adding click listener to Sign up button.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(OrgName.this, SignUp.class);
                startActivity(intent);
            }
        });
    }


    public void setName(){
        user = mAuth.getCurrentUser();
        if(name != null) {
            String orgname = name.getText().toString().trim();
            Toast.makeText(OrgName.this, orgname, Toast.LENGTH_LONG).show();
            if (user != null) {
                users.child("organizations").child(user.getUid()).child("orgName").setValue(orgname);
            }
        }
    }

    public void onClick(View view) {
        if(view == cont){
            //closing activity
            setName();
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileOrg.class));
        }
        if(view == back){
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SignUp.class));
        }
    }
}
