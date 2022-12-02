package com.mobileappdev.myapplication;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Bitmap> mSprites;
    private MineGame mGame;
    private GridLayout mGameGrid;
    private boolean firstClick;

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

                gridButton.setOnLongClickListener(this::onButtonLongClick);
        }

        mGame = new MineGame();
        startGame();
        // mGame.newGame();
    }

    private void startGame() {
        firstClick = true;
    }

    private boolean onButtonLongClick(View view) {
        if(!mGame.isGameOver()) {
            flag(view);
        }
        return true;
    }

    private void onButtonClick(View view) {
        if(firstClick){
            int buttonIndex = mGameGrid.indexOfChild(view);
            int row = buttonIndex / MineGame.GRID_HEIGHT;
            int col = buttonIndex % MineGame.GRID_WIDTH;

            mGame.newGame(row ,col);
            revealTile(view);
            showAdjacentBlanks();
            firstClick = false;
        } else if
        (!mGame.isGameOver()) {
            revealTile(view);
            showAdjacentBlanks();
        }

        if(mGame.isGameWon()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        } else if(mGame.isGameLost()) {
            revealGrid();
            Toast.makeText(this, R.string.nextTime, Toast.LENGTH_SHORT).show();
        }

    }

    public void onHelpClick(View view) {
//        Intent intent = new Intent(this, HelpActivity.class);
//        startActivity(intent);
        revealGrid();
    }


    public void onNewGameClick(View view) {
        hideGrid();
        startGame();
        //revealGrid();
    }


    //TODO: Add method to reveal adjacent blank tiles on blank tile click
    public void revealTile(View view) {
        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

        if(!mGame.isFlagged(row, col)) {
            int tileValue = mGame.getTileValue(row, col);
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
            gridButton.setImageBitmap(getImageFromValue(tileValue));

            if(tileValue == Tile.BLANK)
                mGame.revealAdjacentBlanks(row, col);

            mGame.setTileRevealed(row, col);
        }
    }

    public void showAdjacentBlanks() {
        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            //find the buttons row and col
            int row = buttonIndex / MineGame.GRID_HEIGHT;
            int col = buttonIndex % MineGame.GRID_WIDTH;

            int tileValue = mGame.getTileValue(row, col);
            if(mGame.isTileRevealed(row, col) == true)
                gridButton.setImageBitmap(getImageFromValue(tileValue));
        }
    }

    public void flag(View view) {
        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

        if(!mGame.isTileRevealed(row, col)) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            if(mGame.setFlag(row, col)) {
                gridButton.setImageBitmap(mSprites.get("flag"));
            } else {
                gridButton.setImageBitmap(mSprites.get("unpressed"));
            }
        }
    }

    public void revealGrid() {
        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            //find the buttons row and col
            int row = buttonIndex / MineGame.GRID_HEIGHT;
            int col = buttonIndex % MineGame.GRID_WIDTH;

            int tileValue = mGame.getTileValue(row, col);
            gridButton.setImageBitmap(getImageFromValue(tileValue));
        }
    }

    public void hideGrid() {
        for(int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
            gridButton.setImageBitmap(mSprites.get("unpressed"));
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