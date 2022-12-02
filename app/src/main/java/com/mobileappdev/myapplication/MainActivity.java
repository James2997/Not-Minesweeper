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
            if(buttonIndex == 0)
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
        return true;
    }

    private void onButtonClick(View view) {
<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes

        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

        if (mGame.getTileValue(row, col) == Tile.BLANK)
        {
            revealTile(row, col);
        }
        if (mGame.getTileValue(row, col) == Tile.BOMB)
        {
            revealTile(row, col);
            //revealGrid();
        }
        else
        {
            revealTile(row, col);
            mGame.setScore(mGame.getTileValue(row, col));
        }

//        mGame.selectLight(row, col);
//
//        if (mGame.isGameOver()) {
//            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
//        }

    }

    public void onHelpClick(View view) {
        //Intent intent = new Intent(this, HelpActivity.class);
        //startActivity(intent);
        revealGrid(); /// just testing the reveal
    }

    public void onNewGameClick(View view) {
        startGame();
        //revealGrid();
    }

<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes

    public void revealGrid() {
        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            //find the buttons row and col
            int col = buttonIndex / MineGame.GRID_WIDTH;
            int row = buttonIndex % MineGame.GRID_HEIGHT;

            int tileValue = mGame.getTileValue(row, col);
            switch (tileValue) {
                case Tile.BOMB:
                    gridButton.setImageBitmap(mSprites.get("bomb"));
                    break;
                case Tile.BLANK:
                    gridButton.setImageBitmap(mSprites.get("empty"));
                    break;
                case 1:
                    gridButton.setImageBitmap(mSprites.get("one"));
                    break;
                case 2:
                    gridButton.setImageBitmap(mSprites.get("two"));
                    break;
                case 3:
                    gridButton.setImageBitmap(mSprites.get("three"));
                    break;
                case 4:
                    gridButton.setImageBitmap(mSprites.get("four"));
                    break;
                case 5:
                    gridButton.setImageBitmap(mSprites.get("five"));
                    break;
                case 6:
                    gridButton.setImageBitmap(mSprites.get("six"));
                    break;
                case 7:
                    gridButton.setImageBitmap(mSprites.get("seven"));
                    break;
                case 8:
                    gridButton.setImageBitmap(mSprites.get("eight"));
                    break;
                default: gridButton.setImageBitmap(mSprites.get("bomb_hit"));
                    break;
            }
        }
    }

    public void revealTile(int row, int col) {
        int buttonIndex = (row*mGame.GRID_HEIGHT + col);
        ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
        int tileValue = mGame.getTileValue(row, col);
        switch (tileValue) {
            case Tile.BOMB:
                gridButton.setImageBitmap(mSprites.get("bomb"));
                break;
            case Tile.BLANK:
                gridButton.setImageBitmap(mSprites.get("empty"));
                break;
            case 1:
                gridButton.setImageBitmap(mSprites.get("one"));
                break;
            case 2:
                gridButton.setImageBitmap(mSprites.get("two"));
                break;
            case 3:
                gridButton.setImageBitmap(mSprites.get("three"));
                break;
            case 4:
                gridButton.setImageBitmap(mSprites.get("four"));
                break;
            case 5:
                gridButton.setImageBitmap(mSprites.get("five"));
                break;
            case 6:
                gridButton.setImageBitmap(mSprites.get("six"));
                break;
            case 7:
                gridButton.setImageBitmap(mSprites.get("seven"));
                break;
            case 8:
                gridButton.setImageBitmap(mSprites.get("eight"));
                break;
            default:
                gridButton.setImageBitmap(mSprites.get("bomb_hit"));
                break;
        }
    }
}