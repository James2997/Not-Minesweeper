package com.mobileappdev.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private final String GAME_STATE = "gameState";
    private LightsOutGame mGame;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;
    private int mLightOnColorId = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLightOnColorId = R.color.yellow;

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.height = (int) ((270/LightsOutGame.GRID_SIZE) * getResources().getDisplayMetrics().density + 0.5f);
        layout.width = (int) ((270/LightsOutGame.GRID_SIZE) * getResources().getDisplayMetrics().density + 0.5f);
        layout.setMargins(5, 5, 5, 5);
        mLightGrid = findViewById(R.id.light_grid);
        mLightGrid.setRowCount(LightsOutGame.GRID_SIZE);
        mLightGrid.setColumnCount(LightsOutGame.GRID_SIZE);
        mLightGrid.setUseDefaultMargins(false);

        for (int i = 0; i < LightsOutGame.GRID_SIZE * LightsOutGame.GRID_SIZE; i++) {
            Button newButton = new Button(this);
            newButton.setLayoutParams(layout);
            mLightGrid.addView(newButton);
        }

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onLightButtonClick);
            if(buttonIndex == 0)
                gridButton.setOnLongClickListener(this::onLightButtonLongClick);
        }

        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();

        if(savedInstanceState == null) {
            startGame();
        } else {
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(gameState);
            setButtonColors();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    private boolean onLightButtonLongClick(View view) {
        mGame.cheat();
        setButtonColors();
        return true;
    }

    private void onLightButtonClick(View view) {

        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        mGame.selectLight(row, col);
        setButtonColors();

        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }


    private void setButtonColors() {

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);

            int row = buttonIndex / LightsOutGame.GRID_SIZE;
            int col = buttonIndex % LightsOutGame.GRID_SIZE;

            if (mGame.isLightOn(row, col)) {
                gridButton.setBackgroundColor(mLightOnColor);
            } else {
                gridButton.setBackgroundColor(mLightOffColor);
            }
        }
    }

    public void onChangeColorClick(View view) {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.EXTRA_COLOR, mLightOnColorId);
        mColorResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            mLightOnColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
                            mLightOnColor = ContextCompat.getColor(MainActivity.this, mLightOnColorId);
                            setButtonColors();
                        }
                    }
                }
            });

    public void onNewGameClick(View view) {
        startGame();
    }
}