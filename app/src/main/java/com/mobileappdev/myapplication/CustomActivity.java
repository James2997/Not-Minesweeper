package com.mobileappdev.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class CustomActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;
    boolean isDefault = true;
    int tempMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        seekBar = (SeekBar)findViewById(R.id.seekBarID);
        textView = (TextView)findViewById(R.id.CustomPrompt1);

        seekBar.setMax(MainActivity.mGame.getGridHeight()*MainActivity.mGame.getGridWidth()-(MainActivity.mGame.getGridHeight()+MainActivity.mGame.getGridWidth())+1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textView.setText("# of Mines: " + String.valueOf(i));
                tempMine = i;
                isDefault = false;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onCustomApplyClick(View view) {
        MainActivity.mGame.setTotalBombs(tempMine);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDefaultClick(View view) {
        tempMine = 15;
        isDefault = true;
        MainActivity.mGame.setTotalBombs(tempMine);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}