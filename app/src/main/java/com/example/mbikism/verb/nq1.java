package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class nq1 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nq1);
        Count.npCount = 0;
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneA = (Button) findViewById(R.id.NQ1);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 1;
                Intent intent = new Intent(nq1.this, nq2.class);
                startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.NQ2);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 4;
                Intent intent = new Intent(nq1.this, nq2.class);
                startActivity(intent);
            }
        });
        questionOneE = (Button) findViewById(R.id.NQ3);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 2;
                Intent intent = new Intent(nq1.this, nq2.class);
                startActivity(intent);
            }
        });

        questionOneP = (Button) findViewById(R.id.NQ4);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 3;
                Intent intent = new Intent(nq1.this, nq2.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.NQ5);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.npCount = Count.npCount + 5;
                Intent intent = new Intent(nq1.this, nq2.class);
                startActivity(intent);
            }
        });
    }
}