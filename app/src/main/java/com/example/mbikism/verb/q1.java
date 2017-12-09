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

public class q1 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    private FirebaseAuth mAuth;

    private DatabaseReference database;
    private DatabaseReference users;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q1);

        Count.allCount = 0;

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, Login.class));

            // Showing toast message.
            Toast.makeText(q1.this, "Please Log in to continue", Toast.LENGTH_LONG).show();
        }
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference();
        users = database.child("users");

        users.child(user.getUid()).child("quizScore").setValue(0);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneP = (Button) findViewById(R.id.QB1);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q1.this, q2.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QB2);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q1.this, q2.class);
                startActivity(intent);
            }
        });
        questionOneE = (Button) findViewById(R.id.QB3);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q1.this, q2.class);
                startActivity(intent);
            }
        });

        questionOneY = (Button) findViewById(R.id.QB4);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q1.this, q2.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.QB5);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q1.this, q2.class);
                startActivity(intent);
            }
        });
    }
}