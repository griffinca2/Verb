package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class q10 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;
    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private DatabaseReference users;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q10);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(q10.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        addListenerOnButton();
    }
    public void addListenerOnButton() {
        questionOneE = (Button) findViewById(R.id.QTen1);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                //Update the profile with the new information
                updateQuizCount();
            }
        });
        questionOneP = (Button) findViewById(R.id.QTen2);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                FirebaseUser user = mAuth.getCurrentUser();
                //Toast.makeText(q10.this, Count.allCount, Toast.LENGTH_LONG).show();
                //Update the profile with the new information
                updateQuizCount();
            }
        });
        questionOneA = (Button) findViewById(R.id.QTen3);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                FirebaseUser user = mAuth.getCurrentUser();
                //Toast.makeText(q10.this, Count.allCount, Toast.LENGTH_LONG).show();
                //Update the profile with the new information
                updateQuizCount();
            }
        });

        questionOneS = (Button) findViewById(R.id.QTen4);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                FirebaseUser user = mAuth.getCurrentUser();
                //Update the profile with the new information
               // Toast.makeText(q10.this, Count.allCount, Toast.LENGTH_LONG).show();
                updateQuizCount();
            }
        });
        questionOneY = (Button) findViewById(R.id.QTen5);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                FirebaseUser user = mAuth.getCurrentUser();
                //Update the profile with the new information
                updateQuizCount();
            }
        });
    }

    public void updateQuizCount(){
        if(user != null) {
            users.child("volunteers").child(user.getUid()).child("quizScore").setValue(Count.allCount);
            Intent intent = new Intent(q10.this, ViewResultsActivity.class);
            startActivity(intent);
        }
    }
}