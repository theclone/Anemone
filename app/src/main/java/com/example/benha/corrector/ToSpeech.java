package com.example.benha.corrector;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
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

    public static void init(Context context) {
        instance = new ToSpeech(context);
    }

    public void speak() {
        for (String word: toSpeak) {
            speak(word, QUEUE_ADD, null);
            toSpeak.remove(0);
        }
    }

    public static ToSpeech getInstance() {
        return instance;
    }
}
