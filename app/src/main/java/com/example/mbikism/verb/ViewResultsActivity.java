package com.example.mbikism.verb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ViewResultsActivity extends AppCompatActivity {
    Button viewR;
    private Button profile;
    private Button discover;
    private TextView score;

    private FirebaseAuth mAuth;

    private DatabaseReference database;
    private DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_results);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        viewR = (Button) findViewById(R.id.VR1);
        profile = (Button) findViewById(R.id.profile);
        discover = (Button) findViewById(R.id.discover);

        viewR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewResultsActivity.this, Results.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ViewResultsActivity.this, ProfileVol.class);
                startActivity(intent);

            }
        });
        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Closing current activity.
                finish();
                // Opening the Main Activity .
                Intent intent = new Intent(ViewResultsActivity.this, DiscoverPage.class);
                startActivity(intent);

            }
        });
    }
}