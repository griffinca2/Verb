package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class nq5 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    private FirebaseAuth mAuth;
    private DatabaseReference database;
    private DatabaseReference users;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nq5);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(nq5.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        user = mAuth.getCurrentUser();

        questionOneA = (Button) findViewById(R.id.q5n1);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 1;
                updateQuizCount();
                //Intent intent = new Intent(nq5.this, ProfileOrg.class);
                //startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.q5n2);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 4;
                updateQuizCount();
                //Intent intent = new Intent(nq5.this, ProfileOrg.class);
                //startActivity(intent);
            }
        });
        questionOneE = (Button) findViewById(R.id.q5n3);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 2;
                updateQuizCount();
                //Intent intent = new Intent(nq5.this, ProfileOrg.class);
                //startActivity(intent);
            }
        });

        questionOneP = (Button) findViewById(R.id.q5n4);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 3;
                updateQuizCount();
                //Intent intent = new Intent(nq5.this, ProfileOrg.class);
                //startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.q5n5);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 5;
                updateQuizCount();
                //Intent intent = new Intent(nq5.this, ProfileOrg.class);
                //startActivity(intent);
            }
        });
    }

    public void updateQuizCount(){
        if(user != null) {
            users.child("organizations").child(user.getUid()).child("quizScore").setValue(Count.npCount);
            Intent intent = new Intent(nq5.this, ProfileOrg.class);
            startActivity(intent);
        }
    }
}