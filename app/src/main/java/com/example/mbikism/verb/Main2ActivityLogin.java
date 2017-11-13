package com.example.mbikism.verb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class Main2ActivityLogin extends AppCompatActivity{
    private Button login;
    private Button signup;
    private EditText loginEmail;
    private EditText loginPassword;
    private String emailHolder;
    private String passwordHolder;
    Boolean EditTextEmptyCheck;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //initializing views
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);

        progressDialog = new ProgressDialog(this);

        //initializing firebase auth object
        mAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(mAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), Main3ActivityUserProf.class));
        }

        // Adding click listener to login button.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if(EditTextEmptyCheck) {
                    // If  EditTextEmptyCheck == true then login function called.
                    LoginFunction();
                }
                else {
                    // If  EditTextEmptyCheck == false then toast display on screen.
                    Toast.makeText(Main2ActivityLogin.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Adding click listener to Sign up button.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(Main2ActivityLogin.this, Main4ActivitySignUp.class);
                startActivity(intent);

            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting value form Email's EditText and fill into EmailHolder string variable.
        emailHolder = loginEmail.getText().toString().trim();
        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        passwordHolder = loginPassword.getText().toString().trim();
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
    public void LoginFunction(){
        // Setting up message in progressDialog.
        progressDialog.setMessage("Please Wait");

        // Showing progressDialog.
        progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        mAuth.signInWithEmailAndPassword(emailHolder, passwordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If task done Successful.
                        if(task.isSuccessful()){
                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            // Closing the current Login Activity.
                            finish();
                            // Opening the UserProfileActivity.
                            Intent intent = new Intent(Main2ActivityLogin.this, Main3ActivityUserProf.class);
                            startActivity(intent);
                        }
                        else {
                            // Hiding the progress dialog.
                            progressDialog.dismiss();
                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(Main2ActivityLogin.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
