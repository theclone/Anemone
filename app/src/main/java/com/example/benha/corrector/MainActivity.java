package com.example.benha.corrector;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
        startMic();


        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMic() {
        // Get reference
        final Button micButton = (Button) findViewById(R.id.mic);
        // All of this is one class?
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this.getApplicationContext());
                Log.d("Available:", SpeechRecognizer.isRecognitionAvailable(MainActivity.this) + " ");
                if (SpeechRecognizer.isRecognitionAvailable(MainActivity.this) == true) {
                    SpeechRecognizer.startListening(i);
                }

            }

        });

    }


}
