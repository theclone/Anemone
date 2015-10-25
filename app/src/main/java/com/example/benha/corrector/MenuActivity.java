package com.example.benha.corrector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Castigo");

        Button pronunciation = (Button) findViewById(R.id.button);
        final Intent intent1 = new Intent(this,RecognitionActivity.class);
        pronunciation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        Button vocab = (Button) findViewById(R.id.button2);
        final Intent intent2 = new Intent(this,VocabListActivity.class);
        vocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

        Button cards = (Button) findViewById(R.id.button3);
    }

}
