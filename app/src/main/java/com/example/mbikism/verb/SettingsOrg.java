package com.example.mbikism.verb;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SettingsOrg extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    //view objects
    private TextView email;
    private TextView orgNameView;
    private TextView aboutView;

    private Button changePic;
    private Button submit;
    private Button back;
    private Button discovery;

    private String nameHolder;
    private String emailHolder;
    private String aboutText;
    private String aboutHolder;
    //private String orgName;
    private int cat = 0;

    private ProgressDialog progressDialog;

    private static final int SELECT_PICTURE = 1;
    private ImageView profilePic;
    private ImageView imageView;

    private DatabaseReference database;
    private DatabaseReference users;

    private Uri imageUri2;
    private StorageReference store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_org);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();


        //initializing views
        email = (TextView) findViewById(R.id.userEmail);
        orgNameView = (TextView) findViewById(R.id.orgName);
        aboutView = (TextView) findViewById(R.id.about);
        submit = (Button) findViewById(R.id.buttonSubmit);
        back = (Button) findViewById(R.id.buttonBack);
        discovery = (Button) findViewById(R.id.discovery);
        changePic = (Button) findViewById(R.id.changePic);
        profilePic = (ImageView) findViewById(R.id.profilePic);

        store = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        imageView = (ImageView) findViewById(android.R.id.icon);

        //Reference to database
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        //if the user is not logged in
        //that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(SettingsOrg.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("user");

        //displaying logged in user name
        getInfo();
        //email.setText(user.getEmail());
        //orgNameView.setText(orgName);
        //about.setText(aboutText);
        displayUpdates();

        // Adding click listener on logout button.
        submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Update the user profile
                            changeEmail();
                            changeName();
                            changeAbout();
                            displayUpdates();
                            //Toast.makeText(SettingsOrg.this, "Name updated: " + orgName, Toast.LENGTH_LONG).show();
            // Finishing current User Profile activity.
            finish();
            //Update user info and redirect to user profile
            Intent intent = new Intent(SettingsOrg.this, ProfileOrg.class);
            startActivity(intent);
            // Showing toast message on logout.
            Toast.makeText(SettingsOrg.this, "Settings Updated Successfully.", Toast.LENGTH_LONG).show();
                    }
                });

                // Adding click listener on logout button.
        back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Finishing current User Profile activity.
                            finish();
                            //Update user info and redirect to user profile
                            Intent intent = new Intent(SettingsOrg.this, ProfileOrg.class);
                            startActivity(intent);
                        }
                });

        discovery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Closing current activity.
                            finish();
                            // Opening the Main Activity .
                            Intent intent = new Intent(SettingsOrg.this, DiscoverPage.class);
                            startActivity(intent);
                        }
                });

                changePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updatePic();
                            // Closing current activity.
                            finish();
                            // Opening the Main Activity .
                            //Intent intent = new Intent(SettingsVol.this, Discovery.class);
                            //startActivity(intent);
                        }
                });
        }

    public void displayUpdates(){
        FirebaseUser user = mAuth.getCurrentUser();
        getInfo();
        email.setText(user.getEmail());

        FirebaseUser fuser = mAuth.getCurrentUser();
        if (fuser != null) {
            users.child(fuser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        //User user= dataSnapshot.getValue(User.class);
                        User user1 = dataSnapshot.getValue(User.class);
                        if (user1 != null) {
                            Toast.makeText(SettingsOrg.this, user1.orgName, Toast.LENGTH_LONG).show();
                            orgNameView.setText(user1.orgName);
                            Toast.makeText(SettingsOrg.this, user1.about, Toast.LENGTH_LONG).show();
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

    public void changeName(){
        //update the user info
        FirebaseUser user = mAuth.getCurrentUser();
        //store the strings from email and name into variables
        nameHolder = orgNameView.getText().toString().trim();
        Toast.makeText(SettingsOrg.this, nameHolder, Toast.LENGTH_LONG).show();

        //Update the profile with the new information
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameHolder).build();
        user.updateProfile(profileUpdates);
        users.child("organizations").child(user.getUid()).child("orgName").setValue(nameHolder);
        //Toast.makeText(SettingsVol.this, user.getDisplayName(), Toast.LENGTH_LONG).show();

    }

    public void changeEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(SettingsOrg.this, email.getText().toString().trim(), Toast.LENGTH_LONG).show();
        //store the strings from email and name into variables
        emailHolder = email.getText().toString().trim();
        if(user != null) {
            user.updateEmail(emailHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(SettingsOrg.this, "Email updated succesfully", Toast.LENGTH_LONG).show();
                                        }
                            }
                        });
        }
    }

    public void changeAbout(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(SettingsOrg.this, aboutHolder, Toast.LENGTH_LONG).show();
        aboutHolder = aboutView.getText().toString().trim();
        users.child("organizations").child(user.getUid()).child("about").setValue(aboutHolder);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null){
            Uri pic = data.getData();
            profilePic.setImageURI(pic);

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(pic).build();
        }
    }

    public void updatePic(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_PICTURE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = user.getPhotoUrl();
    }

    public void onClick(View view) {
        //if logout is pressed
        if(view == submit){
            changeName();
            changeEmail();
            changeAbout();
            updatePic();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SettingsVol.class));
        }
        else if(view == back){
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileOrg.class));
        }
        else if(view == changePic){
            updatePic();
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileOrg.class));
        }
    }

    public void getInfo() {
        FirebaseUser fuser = mAuth.getCurrentUser();
        if (fuser != null) {
            users.child(fuser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        //User user= dataSnapshot.getValue(User.class);
                        User user1 = dataSnapshot.getValue(User.class);
                        if (user1 != null) {
                            String category = user1.category;
                            String name = user1.orgName;
                            String a = user1.about;
                            //Toast.makeText(SettingsOrg.this, "Category" + category, Toast.LENGTH_LONG).show();
                            setCat();
                            getName(name);
                            getAbout(a);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });
        }
    }

    public void setCat(){
        cat = 0;
    }

    public String getName(String name) {
        return name;
    }
    public String getAbout(String a) {
        return a;
    }
}

