package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class nq4 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nq4);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneA = (Button) findViewById(R.id.q4n1);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 1;
                //Toast.makeText(nq4.this, Count.npCount, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(nq4.this, nq5.class);
                startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.q4n2);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 4;
                //Toast.makeText(nq4.this, Count.npCount, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(nq4.this, nq5.class);
                startActivity(intent);
            }
        });
        questionOneE = (Button) findViewById(R.id.q4n3);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 2;
                //Toast.makeText(nq4.this, Count.npCount, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(nq4.this, nq5.class);
                startActivity(intent);
            }
        });

        questionOneP = (Button) findViewById(R.id.q4n4);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 3;
                //Toast.makeText(nq4.this, Count.npCount, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(nq4.this, nq5.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.q4n5);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 5;
                //Toast.makeText(nq4.this, Count.npCount, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(nq4.this, nq5.class);
                startActivity(intent);
            }
        });
    }
}