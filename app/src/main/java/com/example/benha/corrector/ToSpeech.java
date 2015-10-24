package com.example.benha.corrector;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class ToSpeech extends TextToSpeech {

    private static ToSpeech instance;
    private static final String TAG = "ToSpeech";
    public ArrayList<String> toSpeak = new ArrayList<>();

    private ToSpeech (Context context) {
        super(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == 1)
                    Log.d(TAG, "ToSpeech initialized");
            }
        });
    }

    // establish Context to instantiate text to speech
    public static void init(Context context) {
        instance = new ToSpeech(context);
    }

    // loops through the toSpeak queue, speaking each String with a delay in milliseconds
    public void speak(final int delay) {
        for (String word: toSpeak) {
            speak(word, QUEUE_ADD, null);
            UtteranceProgressListener listener = new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Log.d("toSpeak","Playing");
                }

                @Override
                public void onDone(String utteranceId) {
                    Log.d("toSpeak","Done");
                    toSpeak.remove(0);
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        // carry on
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    Log.d("toSpeak","RIP");
                }
            };
        }
    }

    // to access instance of class
    public static ToSpeech getInstance() {
        return instance;
    }
}
