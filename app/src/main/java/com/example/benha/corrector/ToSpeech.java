package com.example.benha.corrector;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.ArrayList;

import com.dolby.dap.*;

public class ToSpeech extends TextToSpeech implements OnDolbyAudioProcessingEventListener{

    private static ToSpeech instance;
    private static DolbyAudioProcessing dap;
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
        dap = DolbyAudioProcessing.getDolbyAudioProcessing(context, DolbyAudioProcessing.PROFILE.VOICE, instance);
        dap.setEnabled(true);
        instance = new ToSpeech(context);
    }

    public static void release() {
        instance = null;
        dap.release();
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

    @Override
    public void onDolbyAudioProcessingClientConnected() {

    }

    @Override
    public void onDolbyAudioProcessingClientDisconnected() {

    }

    @Override
    public void onDolbyAudioProcessingEnabled(boolean b) {
        if (b)
            Log.d("Dolby","on");
    }

    @Override
    public void onDolbyAudioProcessingProfileSelected(DolbyAudioProcessing.PROFILE profile) {

    }
}
