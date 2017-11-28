package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q1 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q1);
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