package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewResultsActivity extends AppCompatActivity {
    Button viewR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        viewR = (Button) findViewById(R.id.VR1);
        viewR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewResultsActivity.this, ViewResultsActivity.class);
                startActivity(intent);
            }
        });
    }
}