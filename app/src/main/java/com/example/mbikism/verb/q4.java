package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q4 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q4);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneY = (Button) findViewById(R.id.QFour1);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q4.this, q5.class);
                startActivity(intent);
            }
        });
        questionOneP = (Button) findViewById(R.id.QFour2);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q4.this, q5.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.QFour3);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q4.this, q5.class);
                startActivity(intent);
            }
        });

        questionOneE = (Button) findViewById(R.id.QFour4);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q4.this, q5.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QFour5);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q4.this, q5.class);
                startActivity(intent);
            }
        });
    }
}