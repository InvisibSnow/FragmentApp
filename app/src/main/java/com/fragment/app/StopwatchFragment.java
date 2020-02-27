package com.fragment.app;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener{

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != savedInstanceState){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        Button startButton = layout.findViewById(R.id.btn_start);
        startButton.setOnClickListener(this);
        Button stopButton = layout.findViewById(R.id.btn_stop);
        stopButton.setOnClickListener(this);
        Button resetButton = layout.findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(this);
        runTimer(layout);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    private void onClickStart(){
        running = true;
    }

    private void onClickStop(){
        running = false;
    }

    private void onClickReset(){
        running = false;
        seconds = 0;
    }

    private void runTimer(View view){
        final TextView timeView = (TextView) view.findViewById(R.id.tv_time);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
             int hours = seconds/3600;
             int minutes = (seconds%3600) / 60;
             int secs = seconds % 60;
             String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes, secs);
             timeView.setText(time);
             if(running){
                 seconds++;
             }
             handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                onClickStart();
                break;
            case R.id.btn_stop:
                onClickStop();
                break;
            case R.id.btn_reset:
                onClickReset();
                break;
        }
    }
}
