package com.mobileappdev.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

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
        layout.width = (int) ((270/ MineGame.GRID_HEIGHT) * getResources().getDisplayMetrics().density + 0.5f);
        layout.height = (int) ((270/ MineGame.GRID_HEIGHT) * getResources().getDisplayMetrics().density + 0.5f);
        mGameGrid = findViewById(R.id.game_grid);
        mGameGrid.setColumnCount(MineGame.GRID_HEIGHT);
        mGameGrid.setRowCount(MineGame.GRID_WIDTH);
        mGameGrid.setUseDefaultMargins(false);

        for (int i = 0; i < MineGame.GRID_HEIGHT * MineGame.GRID_WIDTH; i++) {
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
        mGame.newGame();
    }

    private void startGame() {
        mGame.newGame();
    }

    private boolean onButtonLongClick(View view) {
        return true;
    }

    private void onButtonClick(View view) {

        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;
        int tileValue = mGame.getTileValue(row, col);

//        if (mGame.isGameOver() || tileValue == -1) {
//            revealGrid();
//            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
//        }

    }

    public void onHelpClick(View view) {
//        Intent intent = new Intent(this, HelpActivity.class);
//        startActivity(intent);
        revealGrid();
    }

    public void onNewGameClick(View view) {
        startGame();
        revealGrid();
    }

    public void revealTile(int tileValue, int buttonIndex) {

    }

    public void revealGrid() {
        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            //find the buttons row and col
            int col = buttonIndex / MineGame.GRID_WIDTH;
            int row = buttonIndex % MineGame.GRID_HEIGHT;

            int tileValue = mGame.getTileValue(row, col);
            gridButton.setImageBitmap(getImageFromValue(tileValue));
        }
    }

    public Bitmap getImageFromValue(int tileValue) {
        switch (tileValue) {
            case Tile.Mine:
                return mSprites.get("mine");
            case Tile.BLANK:
                return mSprites.get("empty");
            case 1:
                return mSprites.get("one");
            case 2:
                return mSprites.get("two");
            case 3:
                return mSprites.get("three");
            case 4:
                return mSprites.get("four");
            case 5:
                return mSprites.get("five");
            case 6:
                return mSprites.get("six");
            case 7:
                return mSprites.get("seven");
            case 8:
                return mSprites.get("eight");
            default:
                return mSprites.get("mine_hit");
        }
    }
}
