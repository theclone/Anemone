package com.example.benha.corrector;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stefano on 10/24/2015.
 */
public class RecognitionFragment extends Fragment implements RecognitionListener {
    private static RecognitionFragment recognitionFragment;
    EditText editText;
    TextView speakNow, resultTextView, verticalBarScore;
    Button microphone;
    ImageView verticalBarFront, verticalBarBack;
    SpeechRecognizer speechRecognizer;
    Intent recognizerIntent;
    String speechResult;

    public static RecognitionFragment newInstance() {
        if (recognitionFragment == null) {
            recognitionFragment = new RecognitionFragment();
        }
        return recognitionFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recognition_fragment, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

        editText = (EditText) view.findViewById(R.id.editText);
        speakNow = (TextView) view.findViewById(R.id.speakNow);
        resultTextView = (TextView) view.findViewById(R.id.resultView);
        microphone = (Button) view.findViewById(R.id.mic);
        verticalBarFront = (ImageView) view.findViewById(R.id.verticalBarFront);
        verticalBarBack = (ImageView) view.findViewById(R.id.verticalBarBack);
        verticalBarScore = (TextView) view.findViewById(R.id.verticalBarScore);

        verticalBarFront.setVisibility(View.GONE);
        verticalBarBack.setVisibility(View.GONE);
        speakNow.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);


        microphone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(SpeechRecognizer.isRecognitionAvailable(getActivity())){
                            Log.d("Tag", "start listening");
                            speechRecognizer.setRecognitionListener(RecognitionFragment.this);
                            speechRecognizer.startListening(recognizerIntent);
                        };
                        changeBarHeight(0.7f);
                        changeLayout(false);
                        break;
                    case MotionEvent.ACTION_UP:
                        if(SpeechRecognizer.isRecognitionAvailable(getActivity())){
                            speechRecognizer.stopListening();
                        }
                        changeLayout(true);
                        break;
                }
                return false;
            }
        });
    }

    private void changeLayout(boolean showResult){
        if(showResult){
            verticalBarFront.setVisibility(View.VISIBLE);
            verticalBarBack.setVisibility(View.VISIBLE);
            speakNow.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.VISIBLE);
            verticalBarScore.setVisibility(View.VISIBLE);
        }
        else{
            verticalBarFront.setVisibility(View.GONE);
            verticalBarBack.setVisibility(View.GONE);
            speakNow.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            resultTextView.setVisibility(View.GONE);
            verticalBarScore.setVisibility(View.GONE);
        }
    }

    private void changeBarHeight(float result) {
        int score = Math.round(result * 100);
        verticalBarScore.setText( score + "")
        ;
        int height = verticalBarBack.getLayoutParams().height;
        height *= result;
        verticalBarFront.getLayoutParams().height = height;
        verticalBarFront.requestLayout();
    }


    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("TAG", "on ready");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("TAG", "on beginning");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        //Log.d("TAG", "on rsm changed");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        //Log.d("TAG", "on buffer received");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("TAG", "on endofspeech");
    }

    @Override
    public void onError(int error) {
        Log.d("TAG", "on error");
        switch(error){
            case SpeechRecognizer.ERROR_AUDIO:
                Log.d("error", "error ERROR_AUDIO");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                Log.d("error", "error ERROR_CLIENT");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                Log.d("error", "error ERROR_INSUFFICIENT_PERMISSIONS");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                Log.d("error", "error ERROR_NETWORK");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                Log.d("error", "error ERROR_NETWORK_TIMEOUT");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                Log.d("error", "error ERROR_NO_MATCH");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Log.d("error", "error ERROR_RECOGNIZER_BUSY");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Log.d("error", "error ERROR_SERVER");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                Log.d("error", "error ERROR_SPEECH_TIMEOUT");
                break;
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> strings = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        ToSpeech.getInstance().toSpeak.addAll(strings);
        speechResult = "";
        speechResult = strings.get(0);
        /*
        for(String s : strings) {
            speechResult = speechResult + s + " ";
        }*/
        speechResult = speechResult.trim();
        resultTextView.setText(speechResult);
        Log.d("TAG", "on result");

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.d("TAG", "on partial result");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.d("TAG", "on event");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
    }
}
