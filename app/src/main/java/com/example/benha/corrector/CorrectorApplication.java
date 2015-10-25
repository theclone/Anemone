package com.example.benha.corrector;

import android.app.Application;
import android.util.Log;

import com.dolby.dap.DolbyAudioProcessing;
import com.dolby.dap.OnDolbyAudioProcessingEventListener;

/**
 * Created by Stefano on 10/24/2015.
 */
public class CorrectorApplication extends Application implements OnDolbyAudioProcessingEventListener {
    private final String TAG = "Corrector Application";

    public void onDolbyAudioProcessingEnabled(boolean on) {
        Log.i(TAG, "onDolbyAudioProcessingEnabled(" + on + ")");
    }
    public void onDolbyAudioProcessingProfileSelected(DolbyAudioProcessing.PROFILE profile) {
        Log.i(TAG, "onDolbyAudioProcessingProfileSelected(" + profile + ")");
    }
    public void onDolbyAudioProcessingClientConnected() {
        Log.i(TAG, "onDolbyAudioProcessingClientConnected()");
    }
    public void onDolbyAudioProcessingClientDisconnected() {
        Log.w(TAG, "onDolbyAudioProcessingClientDisconnected()");
    }
}