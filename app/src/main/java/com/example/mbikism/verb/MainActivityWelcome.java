package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

public class MainActivityWelcome extends AppCompatActivity {
    Button takeAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        takeAction = (Button) findViewById(R.id.button2);
        takeAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivityWelcome.this, Main2ActivityLogin.class);
                    startActivity(intent);
            }
        });
    }

}
