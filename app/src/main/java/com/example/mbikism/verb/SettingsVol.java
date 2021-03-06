package com.example.mbikism.verb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.ProgressDialog;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SettingsVol extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private DatabaseReference users;
    private StorageReference storeRef;


    //view objects
    private TextView email;
    private TextView fname;
    private TextView lname;
    private TextView birthday;

    private Button changePic;
    private Button submit;
    private Button back;
    private Button discovery;


    private String fnameHolder;
    private String lnameHolder;
    private String nameHolder;
    private String emailHolder;
    private String bday;

    private ProgressDialog progressDialog;

    private static final int SELECT_PICTURE = 1;
    private ImageView profilePic;
    private ImageView imageView;

    private Uri imageUri2;
    private StorageReference storageRef;
    int Image_Request_Code = 7;

    FirebaseUser user;

    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    String Database_Path = "All_Image_Uploads_Database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_vol);

        //initializing firebase authentication object
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        email = (TextView) findViewById(R.id.userEmail);
        fname = (TextView) findViewById(R.id.fname);
        lname = (TextView) findViewById(R.id.lname);
        submit = (Button) findViewById(R.id.buttonSubmit);
        back = (Button) findViewById(R.id.buttonBack);
        discovery = (Button) findViewById(R.id.discovery);
        changePic = (Button) findViewById(R.id.changePic);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        birthday = (TextView) findViewById(R.id.birthday);

        progressDialog = new ProgressDialog(this);

        imageView = (ImageView) findViewById(android.R.id.icon);

        Uri FilePathUri;
        storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference userRef = storageRef.child("user.png");

        //if the user is not logged in, then the current user will return null
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(SettingsVol.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }

        //getting current user
        FirebaseUser user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        //Display Updated fields
        displayUpdates();

        // Adding click listener on logout button.
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Update the user profile
                changeEmail();
                changeName();
                changeBirthday();
                // Finishing current User Profile activity.
                finish();
                //Update user info and redirect to user profile
                Intent intent = new Intent(SettingsVol.this, ProfileVol.class);
                startActivity(intent);
                // Showing toast message on logout.
                Toast.makeText(SettingsVol.this, "Settings Updated Successfully.", Toast.LENGTH_LONG).show();

            }
        });

        // Adding click listener on logout button.
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finishing current User Profile activity.
                finish();

                //Update user info and redirect to user profile
                Intent intent = new Intent(SettingsVol.this, ProfileVol.class);
                startActivity(intent);
            }
        });

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(SettingsVol.this, DiscoverPage.class);
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

    public void changeName(){
        //update the user info
        FirebaseUser user = mAuth.getCurrentUser();
        //store the strings from email and name into variables
        fnameHolder = fname.getText().toString().trim();
        lnameHolder = lname.getText().toString().trim();
        nameHolder = fnameHolder + " " + lnameHolder;

        //Update the profile with the new information
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameHolder).build();
        user.updateProfile(profileUpdates);

        users.child(user.getUid()).child("firstName").setValue(fnameHolder);
        users.child(user.getUid()).child("lastName").setValue(lnameHolder);
        //Toast.makeText(SettingsVol.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
    }

    public void changeEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(SettingsVol.this, email.getText().toString().trim(), Toast.LENGTH_LONG).show();
        //store the strings from email and name into variables
        emailHolder = email.getText().toString().trim();
        if(user != null) {
            user.updateEmail(emailHolder).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                       // Toast.makeText(SettingsVol.this, "Email updated succesfully", Toast.LENGTH_LONG).show();
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

            /**Uri selectedImage = data.getData();
            //Log.i("MainActivity", "selected Image = "+selectedImage);
            this.profilePic.setImageURI(selectedImage);
            this.uploadImageToFirebase();**/
        }
    }

    /**private void uploadImageToFirebase() {
        // Get the data from an ImageView as bytes
        this.profilePic.setDrawingCacheEnabled(true);
        this.profilePic.buildDrawingCache();
        Bitmap bitmap = this.profilePic.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference newPic = storageRef.child("myimagename.jpg");
        UploadTask uploadTask = newPic.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("MainActivity", "Upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.i("MainActivity", "Upload successful, downloadUrl = "+downloadUrl);
            }
        });
    }**/


    public void updatePic(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_PICTURE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Uri photoUrl = user.getPhotoUrl();
    }

    public void changeBirthday(){
        //update the user info
        FirebaseUser user = mAuth.getCurrentUser();
        //store the strings from email and name into variables
        bday = birthday.getText().toString().trim();

        users.child(user.getUid()).child("birthday").setValue(bday);
    }

    public void onClick(View view) {
        //if logout is pressed
        if(view == submit){
            changeName();
            changeEmail();
            changeBirthday();
            updatePic();
            displayUpdates();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SettingsVol.class));
        }
        else if(view == back){
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileVol.class));
        }
        else if(view == changePic){
            updatePic();
            finish();
            //starting login activity
            startActivity(new Intent(this, ProfileVol.class));
        }
    }

    public void displayUpdates(){
        user = mAuth.getCurrentUser();
        //email.setText(user.getEmail());
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                if (user1 == null) {
                    Toast.makeText(SettingsVol.this, "User is unexpectedly null.", Toast.LENGTH_LONG).show();
                } else {
                    fname.setText(user1.firstName);
                    lname.setText(user1.lastName);
                    birthday.setText(user1.birthday);
                    email.setText(user1.email);
                }
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {}
        };
        users.child(user.getUid()).addListenerForSingleValueEvent(eventListener);
    }
}