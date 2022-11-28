package com.mobileappdev.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Bitmap> mSprites;
    private MineGame mGame;
    private GridLayout mGameGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSprites = ImageProcessing.processSprites(BitmapFactory.decodeResource(getResources(), R.drawable.minesweeper));

        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.width = (int) ((270/ MineGame.GRID_WIDTH) * getResources().getDisplayMetrics().density + 0.5f);
        layout.height = (int) ((270/ MineGame.GRID_WIDTH) * getResources().getDisplayMetrics().density + 0.5f);
        mGameGrid = findViewById(R.id.game_grid);
        mGameGrid.setColumnCount(MineGame.GRID_WIDTH);
        mGameGrid.setRowCount(MineGame.GRID_HEIGHT);
        mGameGrid.setUseDefaultMargins(false);

        for (int i = 0; i < MineGame.GRID_WIDTH * MineGame.GRID_HEIGHT; i++) {
            ImageButton newButton = new ImageButton(this);
            newButton.setImageBitmap(mSprites.get("unpressed"));
            newButton.setPadding(0, 0, 0, 0);
            newButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            newButton.setLayoutParams(layout);
            mGameGrid.addView(newButton);
        }

        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onButtonClick);
            if(buttonIndex == 0)
                gridButton.setOnLongClickListener(this::onButtonLongClick);
        }

        mGame = new MineGame();
    }

    private void startGame() {
        mGame.newGame();
    }

    private boolean onButtonLongClick(View view) {
        return true;
    }

    private void onButtonClick(View view) {

        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_WIDTH;
        int col = buttonIndex % MineGame.GRID_HEIGHT;

        mGame.selectLight(row, col);

        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void onNewGameClick(View view) {
        startGame();
    }
}