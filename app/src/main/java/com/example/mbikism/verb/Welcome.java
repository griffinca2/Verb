package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

public class Welcome extends AppCompatActivity {
    Button takeAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        takeAction = (Button) findViewById(R.id.button2);
        takeAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Welcome.this, Login.class);
                    startActivity(intent);
            }
        });
    }

}
