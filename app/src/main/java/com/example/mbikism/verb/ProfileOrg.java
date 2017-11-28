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

import static com.google.android.gms.internal.zzbco.NULL;

public class ProfileOrg extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;


    //view objects
    private TextView userEmail;
    private Button logout;
    private Button discovery;
    private Button retakeQuiz;
    //private ImageButton settings;
    private ImageButton settings;
    private TextView orgNameView;
    private TextView aboutView;

    private DatabaseReference database;
    private DatabaseReference users;
    private DatabaseReference volunteers;
    private DatabaseReference organizations;

    private int cat;
    private String orgName = "";
    private String fName;
    private String lNamel;
    private String fullName;
    private String aboutText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_org);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();


        //initializing views
        userEmail = (TextView) findViewById(R.id.userEmail);
        orgNameView = (TextView) findViewById(R.id.orgName);
        logout = (Button) findViewById(R.id.buttonLogout);
        discovery = (Button) findViewById(R.id.discovery);
        retakeQuiz = (Button) findViewById(R.id.buttonRetake);
        settings = (ImageButton) findViewById(R.id.buttonSettings);

        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");
        volunteers = users.child("volunteers");
        organizations = users.child("organizations");

        //if the user is not logged in
        //that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(ProfileOrg.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();

        //displaying logged in user name
        displayUpdates();
        //userEmail.setText(user.getEmail());
        //getInfo();
        //name.setText(orgName);


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
                Intent intent = new Intent(ProfileOrg.this, Login.class);
                startActivity(intent);

                // Showing toast message on logout.
                Toast.makeText(ProfileOrg.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener to Sign up button.
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
                Intent intent = new Intent(ProfileOrg.this, DiscoverPage.class);
                startActivity(intent);

            }
        });
        retakeQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ProfileOrg.this, q1.class);
                startActivity(intent);

            }
        });
    }


    public void displayUpdates(){
        FirebaseUser user = mAuth.getCurrentUser();
        getInfo();
        userEmail.setText(user.getEmail());

        FirebaseUser fuser = mAuth.getCurrentUser();
        if (fuser != null) {
            users.child(fuser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        //User user= dataSnapshot.getValue(User.class);
                        User user1 = dataSnapshot.getValue(User.class);
                        if (user1 != null) {
                            orgNameView.setText(user1.orgName);
                            aboutView.setText(user1.about);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });
        }
    }


    public void getInfo(){
        FirebaseUser user = mAuth.getCurrentUser();
        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    //User user= dataSnapshot.getValue(User.class);
                    User user1 = dataSnapshot.getValue(User.class);
                    if(user1 != null) {
                        String category = user1.category;
                        String orgName = user1.orgName;
                        setCat(category);
                        setOrgName(orgName);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }

    public void setCat(String category){
        cat = 0;
    }

    public void setOrgName(String name) {
        FirebaseUser user = mAuth.getCurrentUser();
        orgName = user.getDisplayName();
        //orgName = name;
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
            startActivity(new Intent(this, DiscoverPage.class));
        }
    }
}
