package com.example.mbikism.verb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity{
    private Button login;
    private Button signup;
    private EditText signupEmail;
    private EditText signupPassword;
    private EditText cat;
    private EditText signupFName;
    private EditText signupLName;

    private String emailHolder;
    private String passwordHolder;
    private String nameHolder;
    private String fName;
    private String lName;
    private String catStr;
    Boolean EditTextEmptyCheck;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    private DatabaseReference database;
    private DatabaseReference users;
    private DatabaseReference volunteers;
    private DatabaseReference organizations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //initializing views
        signupEmail = (EditText) findViewById(R.id.signupEmail);
        signupPassword = (EditText) findViewById(R.id.signupPassword);
        signupFName = (EditText) findViewById(R.id.signupFName);
        signupLName = (EditText) findViewById(R.id.signupLName);
        cat = (EditText) findViewById(R.id.category);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);

        progressDialog = new ProgressDialog(this);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //Database references
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");


        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(mAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileVol.class));
        }

        // Adding click listener to signup button.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(EditTextEmptyCheck) {
                    // If  EditTextEmptyCheck == true then signup function called.
                    createAccount();
                }
                else {
                    // If  EditTextEmptyCheck == false then toast display on screen.
                    Toast.makeText(SignUp.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Adding click listener to Log in button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);

            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting value form Email's EditText and fill into EmailHolder string variable.
        emailHolder = signupEmail.getText().toString().trim();
        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        passwordHolder = signupPassword.getText().toString().trim();
        // Getting value form Name's EditText and fill into nameHolder string variable.
        //nameHolder = signupName.getText().toString().trim();
        // Checking Both EditText is empty or not.
        if(TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder))
        {
            // If any of EditText is empty then set value as false.
            EditTextEmptyCheck = false;
        }
        else {
            // If any of EditText is empty then set value as true.
            EditTextEmptyCheck = true ;
        }

    }

    // Creating login function.
    public void createAccount(){
        fName = signupFName.getText().toString().trim();
        lName = signupLName.getText().toString().trim();
        catStr = cat.getText().toString().trim();
        emailHolder = signupEmail.getText().toString().trim();
        nameHolder = fName + " " + lName;

        // Setting up message in progressDialog.
        progressDialog.setMessage("Creating Account Please Wait..");

        // Showing progressDialog.
        progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        mAuth.createUserWithEmailAndPassword(emailHolder, passwordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If task done Successful.
                        if(task.isSuccessful()){
                            // Hiding the progress dialog.
                            progressDialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();
                            /**if(!TextUtils.isEmpty(nameHolder)){
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nameHolder).build();
                                user.updateProfile(profileUpdates);
                                addUserToDatabase(fName, lName, catStr, emailHolder, "");
                            }**/
                            //addUserToDatabase(fName, lName, catStr, emailHolder);
                            // Closing the current Login Activity.
                            // Opening the UserProfileActivity.
                            //Toast.makeText(SignUp.this, "Category: " + catStr, Toast.LENGTH_LONG).show();
                            //Toast.makeText(SignUp.this, catStr.equals("Volunteer") + " ", Toast.LENGTH_LONG).show();
                            if(catStr.equals("Volunteer")) {
                                if(!TextUtils.isEmpty(nameHolder)){
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nameHolder).build();
                                    user.updateProfile(profileUpdates);
                                    addUserToDatabase(fName, lName, catStr, emailHolder, "");
                                }

                                finish();
                                Intent intent = new Intent(SignUp.this, q1.class);
                                startActivity(intent);
                            }
                            else if(catStr.equals("Organization")){
                                if(!TextUtils.isEmpty(nameHolder)){
                                    addUserToDatabase(fName, lName, catStr, emailHolder, "");
                                }

                                finish();
                                Intent intent = new Intent(SignUp.this, nq1.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignUp.this, "Type of user not found. Please Enter 'Volunteer' or 'Organization'.", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(SignUp.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    void addUserToDatabase(String fName, String lName, String category, String email, String birthday){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        String info = " ";
        String orgName = " ";

        //Create user based on whether they are a volunteer or organization.
        if(category.equals("Volunteer")) {
            User newUser = new User(uid, fName, lName, category, email, birthday, 0);
            database.child("users").child(uid).setValue(newUser);
        }
        else if(category.equals("Organization")) {
            User newUser = new User(uid, fName, lName, category, email, orgName, info, 0);
            //database.child("users").child("organizations").child(uid).setValue(newUser);
            database.child("users").child(uid).setValue(newUser);
        }
    }
}
