package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class q2 extends AppCompatActivity {
    Button questionOneP, questionOneA, questionOneE, questionOneY, questionOneS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q2);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        questionOneE = (Button) findViewById(R.id.QTwo1);
        questionOneE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 2;
                Intent intent = new Intent(q2.this, q3.class);
                startActivity(intent);
            }
        });
        questionOneA = (Button) findViewById(R.id.QTwo2);
        questionOneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 1;
                Intent intent = new Intent(q2.this, q3.class);
                startActivity(intent);
            }
        });
        questionOneS = (Button) findViewById(R.id.QTwo3);
        questionOneS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 5;
                Intent intent = new Intent(q2.this, q3.class);
                startActivity(intent);
            }
        });

        questionOneY = (Button) findViewById(R.id.QTwo4);
        questionOneY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 4;
                Intent intent = new Intent(q2.this, q3.class);
                startActivity(intent);
            }
        });
        questionOneP = (Button) findViewById(R.id.QTwo5);
        questionOneP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Count.allCount = Count.allCount + 3;
                Intent intent = new Intent(q2.this, q3.class);
                startActivity(intent);
            }
        });
    }
}