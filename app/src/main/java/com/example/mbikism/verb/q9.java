package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q9 extends AppCompatActivity{
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q9);
        addListenerOnButton();
    }
    public void addListenerOnButton() {
        questionOneE = (Button) findViewById(R.id.QNine1);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q9.this, q10.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.QNine2);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q9.this, q10.class);
                startActivity(intent);
            }
        });
        questionOneY = (Button) findViewById(R.id.QNine3);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q9.this, q10.class);
                startActivity(intent);
            }
        });

        questionOneP = (Button) findViewById(R.id.QNine4);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q9.this, q10.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QNine5);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q9.this, q10.class);
                startActivity(intent);
            }
        });
    }

}