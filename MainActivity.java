package com.example.texttospeech;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText text;
    TextView pitch,speed,volumn;
    SeekBar sbpitch,sbspeed,sbvolumn;
    ImageView sayit,clear;
    TextToSpeech textToSpeech;
    float s,p;
    AudioManager audiomanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        pitch = findViewById(R.id.pitch);
        speed = findViewById(R.id.speed);
        volumn = findViewById(R.id.volume);
        sbpitch = findViewById(R.id.seekBarPitch);
        sbspeed = findViewById(R.id.seekBarSpeed);
        sbvolumn = findViewById(R.id.seekBarVolme);
        sayit = findViewById(R.id.sayit);
        clear = findViewById(R.id.clear);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i== textToSpeech.SUCCESS){
                    //select Language
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);

                }
            }
        });
        sayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get Edit TextValue
                String str = text.getText().toString();
                float p = (float) sbpitch.getProgress() /50;
                if(p < 0.1 ) p=0.1f;
                float s = (float) sbspeed.getProgress() /50;
                if(s < 0.1 ) s=0.1f;
                textToSpeech.setPitch(p);
                textToSpeech.setSpeechRate(s);
                audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                sbvolumn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                      audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                //Text Convert to Speech
                int speech = textToSpeech.speak(str,TextToSpeech.QUEUE_FLUSH,null);
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear Edit Text
                text.setText("");
                sbpitch.setProgress(50);
                sbspeed.setProgress(50);
                sbvolumn.setProgress(50);
            }
        });
    }



}
