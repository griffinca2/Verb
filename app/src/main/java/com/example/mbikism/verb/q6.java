package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q6 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q6);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneP = (Button) findViewById(R.id.QSix1);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q6.this, q7.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QSix2);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q6.this, q7.class);
                startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.QSix3);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q6.this, q7.class);
                startActivity(intent);
            }
        });

        questionOneS = (Button) findViewById(R.id.QSix4);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q6.this, q7.class);
                startActivity(intent);
            }
        });
        questionOneE = (Button) findViewById(R.id.QSix5);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q6.this, q7.class);
                startActivity(intent);
            }
        });
    }
}