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
    private boolean isCheating = false, firstClick = false;

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
    }

    private void startGame() {
        isCheating = false;
        firstClick = true;
    }

    private boolean onButtonLongClick(View view) {
        if(mGame.isGameOver()) {
            return true;
        }
        flag(view);
        return true;
    }

    private void onButtonClick(View view) {
        if(mGame.isGameOver()) {
            return;
        }

        if(firstClick) {
            int buttonIndex = mGameGrid.indexOfChild(view);
            int row = buttonIndex / MineGame.GRID_HEIGHT;
            int col = buttonIndex % MineGame.GRID_WIDTH;

            mGame.newGame(row, col);
            revealTile(view);
            firstClick = false;
        } else {
            revealTile(view);
        }

        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

//        mGame.selectLight(row, col);
//
//        if (mGame.isGameOver()) {
//            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
//        }

    }

    public void onCheatClick(View view) {
        if(!firstClick) {
            cheat();
        }
    }

    public void onNewGameClick(View view) {
        startGame();
    }

    public void revealTile(View view) {
        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

        if(!mGame.isFlagged(row, col)) {
            int tileValue = mGame.getTileValue(row, col);
            if(tileValue == 0) {
                ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
                gridButton.setImageBitmap(getImageFromValue(tileValue));
                mGame.setTileRevealed(row, col);

                for(int i = -1; i <= 1; i++) {
                    for(int j = -1; j <= 1; j++) {
                        if(!(i == 0 && j == 0)) {
                            int nRow = row + i;
                            int nCol = col + j;
                            if((nRow >= 0 && nCol >= 0) && (nRow < MineGame.GRID_HEIGHT && nCol < MineGame.GRID_WIDTH)) {
                                if(mGame.isTileHidden(nRow, nCol)) {
                                    revealTile(getButtonAt(nRow, nCol));
                                }
                            }
                        }
                    }
                }
            } else {
                ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
                gridButton.setImageBitmap(getImageFromValue(tileValue));
                mGame.setTileRevealed(row, col);
            }
        }
    }

    //Sets given ImageButton to a flag image
    //This makes the button non-clickable
    public void flag(View view) {
        int buttonIndex = mGameGrid.indexOfChild(view);
        int row = buttonIndex / MineGame.GRID_HEIGHT;
        int col = buttonIndex % MineGame.GRID_WIDTH;

        if(mGame.isTileHidden(row, col)) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);

            if(mGame.setFlag(row, col)) {
                gridButton.setImageBitmap(mSprites.get("flag"));
            } else {
                gridButton.setImageBitmap(mSprites.get("unpressed"));
            }
        }
    }

    //Reveals the entire board
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

    public void cheat() {
        for (int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
            int row = buttonIndex / MineGame.GRID_HEIGHT;
            int col = buttonIndex % MineGame.GRID_WIDTH;
            int tileValue = mGame.getTileValue(row, col);

            if(isCheating) {
                if(mGame.isTileHidden(row, col)) {
                    if(mGame.isFlagged(row, col)) {
                        gridButton.setImageBitmap(mSprites.get("flag"));
                    } else {
                        gridButton.setImageBitmap(mSprites.get("unpressed"));
                    }
                } else {
                    gridButton.setImageBitmap(getImageFromValue(tileValue));
                }
            } else {
                if(mGame.isFlagged(row, col)) {
                    gridButton.setImageBitmap(mSprites.get("flag"));
                } else {
                    gridButton.setImageBitmap(getImageFromValue(tileValue));
                }
            }
        }
        isCheating = !isCheating;
    }

    //Iterates through all ImageButtons, and sets them to their default state
    public void hideGrid() {
        for(int buttonIndex = 0; buttonIndex < mGameGrid.getChildCount(); buttonIndex++) {
            ImageButton gridButton = (ImageButton) mGameGrid.getChildAt(buttonIndex);
            gridButton.setImageBitmap(mSprites.get("unpressed"));
        }
    }

    //Returns Bitmap image for given tile
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

    //Returns ImageButton view when given it's row and column
    public View getButtonAt(int row, int col) {
        int index = (row * MineGame.GRID_WIDTH) + col;
        return mGameGrid.getChildAt(index);
    }
}
