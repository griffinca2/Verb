package com.example.mbikism.verb;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Main5ActivitySettings extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference database;


    //view objects
    private TextView email;
    private TextView name;

    private Button changePic;
    private Button submit;
    private Button back;
    private Button discovery;


    private String nameHolder;
    private String emailHolder;

    private ProgressDialog progressDialog;

    private static final int SELECT_PICTURE = 1;
    private ImageView profilePic;
    private ImageView imageView;

    private Uri imageUri2;
    private StorageReference store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();


        //initializing views
        email = (TextView) findViewById(R.id.userEmail);
        name = (TextView) findViewById(R.id.name);
        submit = (Button) findViewById(R.id.buttonSubmit);
        back = (Button) findViewById(R.id.buttonBack);
        discovery = (Button) findViewById(R.id.discovery);
        changePic = (Button) findViewById(R.id.changePic);
        profilePic = (ImageView) findViewById(R.id.profilePic);

        store = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);

        imageView = (ImageView) findViewById(android.R.id.icon);

        //userEmail.addTextChangedListener(textWatcher);
        //name.addTextChangedListener(textWatcher);


        //if the user is not logged in
        //that means current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Main2ActivityLogin.class));

            // Showing toast message.
            Toast.makeText(Main5ActivitySettings.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference().child("user");

        //displaying logged in user name
        email.setText(user.getEmail());
        name.setText(user.getDisplayName());

        // Adding click listener on logout button.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update the user profile
                changeEmail();
                changeName();
                // Finishing current User Profile activity.
                finish();
                //Update user info and redirect to user profile
                Intent intent = new Intent(Main5ActivitySettings.this, Main3ActivityUserProf.class);
                startActivity(intent);
                // Showing toast message on logout.
                Toast.makeText(Main5ActivitySettings.this, "Settings Updated Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener on logout button.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finishing current User Profile activity.
                finish();

                //Update user info and redirect to user profile
                Intent intent = new Intent(Main5ActivitySettings.this, Main3ActivityUserProf.class);
                startActivity(intent);
            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Main5ActivitySettings.this, Main6ActivityDiscovery.class);
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
                //Intent intent = new Intent(Main5ActivitySettings.this, Main6ActivityDiscovery.class);
                //startActivity(intent);

            }
        });
    }

    public void changeName(){
        //update the user info
        FirebaseUser user = mAuth.getCurrentUser();
        //store the strings from email and name into variables
        nameHolder = name.getText().toString().trim();

        //Update the profile with the new information
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameHolder).build();
        user.updateProfile(profileUpdates);
        //Toast.makeText(Main5ActivitySettings.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
    }

    public void changeEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(Main5ActivitySettings.this, email.getText().toString().trim(), Toast.LENGTH_LONG).show();
        //store the strings from email and name into variables
        emailHolder = email.getText().toString().trim();
        if(user != null) {
            user.updateEmail(emailHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Main5ActivitySettings.this, "Email updated succesfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            updatePic();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Main5ActivitySettings.class));
        }
        else if(view == back){
            finish();
            //starting login activity
            startActivity(new Intent(this, Main3ActivityUserProf.class));
        }
        else if(view == changePic){
            updatePic();
            finish();
            //starting login activity
            startActivity(new Intent(this, Main3ActivityUserProf.class));
        }
    }
}

