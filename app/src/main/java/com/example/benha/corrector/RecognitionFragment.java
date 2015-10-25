package com.example.benha.corrector;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecognitionFragment extends Fragment implements RecognitionListener {
    private static RecognitionFragment recognitionFragment;
    EditText editText;
    TextView speakNow, resultTextView, verticalBarScore;
    Button microphone;
    ImageView verticalBarFront, verticalBarBack;
    SpeechRecognizer speechRecognizer;
    Intent recognizerIntent;
    ImageView speaker;

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
        ToSpeech.init(this.getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        editText = (EditText) view.findViewById(R.id.editText);
        speakNow = (TextView) view.findViewById(R.id.speakNow);
        resultTextView = (TextView) view.findViewById(R.id.resultView);
        microphone = (Button) view.findViewById(R.id.mic);
        verticalBarFront = (ImageView) view.findViewById(R.id.verticalBarFront);
        verticalBarBack = (ImageView) view.findViewById(R.id.verticalBarBack);
        verticalBarScore = (TextView) view.findViewById(R.id.verticalBarScore);
        speaker = (ImageView) view.findViewById(R.id.speaker);

        verticalBarFront.setVisibility(View.GONE);
        verticalBarBack.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.GONE);
        verticalBarScore.setVisibility(View.GONE);
        speaker.setVisibility(View.VISIBLE);
        speakNow.setText("Click on the microphone and speak or write the sentence you want to listen to");
        speakNow.setVisibility(View.VISIBLE);


        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    ToSpeech.getInstance().toSpeak.add(editText.getText().toString());
                    ToSpeech.getInstance().speak(0);
                }
                else
                    Toast.makeText(getActivity(), "Insert the word to listen to", Toast.LENGTH_SHORT).show();
            }
        });

        resultTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToSpeech.getInstance().toSpeak.add(resultTextView.getText().toString());
            }
        });

        resultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editText.setText(resultTextView.getText());
                return true;
            }
        });
        microphone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (SpeechRecognizer.isRecognitionAvailable(getActivity())) {
                            Log.d("Tag", "start listening");
                            speechRecognizer.setRecognitionListener(RecognitionFragment.this);
                            speechRecognizer.startListening(recognizerIntent);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (SpeechRecognizer.isRecognitionAvailable(getActivity())) {
                            speechRecognizer.stopListening();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void changeLayout(boolean showResult){
        speakNow.setText("Speak now");
        if(showResult){
            verticalBarFront.setVisibility(View.VISIBLE);
            verticalBarBack.setVisibility(View.VISIBLE);
            speakNow.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            resultTextView.setVisibility(View.VISIBLE);
            verticalBarScore.setVisibility(View.VISIBLE);
            verticalBarScore.setVisibility(View.VISIBLE);
            speaker.setVisibility(View.VISIBLE);
        }
        else{
            verticalBarFront.setVisibility(View.GONE);
            verticalBarBack.setVisibility(View.GONE);
            speakNow.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
            resultTextView.setVisibility(View.GONE);
            verticalBarScore.setVisibility(View.GONE);
            verticalBarScore.setVisibility(View.GONE);
            speaker.setVisibility(View.GONE);
        }
    }

    private void changeBarHeight(float result) {
        int score = Math.round(result * 100);
        verticalBarScore.setText(score + "%");
        int height = verticalBarBack.getLayoutParams().height;
        height *= result;
        verticalBarFront.getLayoutParams().height = height;
        verticalBarFront.requestLayout();
    }


    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("TAG", "on ready");
        changeLayout(false);
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
        resultTextView.setText("Processing...");
        resultTextView.setVisibility(View.VISIBLE);
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
                resultTextView.setText("no match, sorry");
                changeBarHeight(0);
                changeLayout(true);
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                Log.d("error", "error ERROR_RECOGNIZER_BUSY");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                Log.d("error", "error ERROR_SERVER");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                Log.d("error", "error ERROR_SPEECH_TIMEOUT");
                resultTextView.setText("no match, sorry");
                changeBarHeight(0);
                changeLayout(true);
                break;
        }
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> strings = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        float[] confidence = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);
        if (strings != null) {
            ToSpeech.getInstance().toSpeak.add(strings.get(0));
            resultTextView.setText(strings.get(0));
            if (confidence != null) {
                changeBarHeight(confidence[0]);
            }
        }
        Log.d("TAG", "on result");
        changeLayout(true);
        ToSpeech.getInstance().speak(0);
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
