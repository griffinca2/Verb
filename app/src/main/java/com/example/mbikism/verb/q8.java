package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q8 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q8);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneE = (Button) findViewById(R.id.QEight1);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q8.this, q9.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.QEight2);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q8.this, q9.class);
                startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.QEight3);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q8.this, q9.class);
                startActivity(intent);
            }
        });

        questionOneP = (Button) findViewById(R.id.QEight4);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q8.this, q9.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QEight5);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q8.this, q9.class);
                startActivity(intent);
            }
        });
    }
}