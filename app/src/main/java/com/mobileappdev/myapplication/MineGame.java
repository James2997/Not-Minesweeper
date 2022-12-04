package com.mobileappdev.myapplication;

import java.util.Random;

public class MineGame {
    public static final int GRID_HEIGHT = 10, GRID_WIDTH = 10;
    public static final int totalBombs = 10;

    private Tile[][] tileGrid;

    public MineGame() {
        tileGrid = new Tile[GRID_HEIGHT][GRID_WIDTH];
    }

    public void newGame(int x, int y) {
        gameWon = false;
        gameLost = false;
        revealedTiles = 0;
        tileGrid = setGrid(x, y);
    }


    // iterates through array and counts surrounding bombs
    private static Tile[][] setGrid(int x, int y){
        Tile[][] grid = makeBlankGrid();
        int minesPlaced = 0;

        while (minesPlaced < totalBombs){
            int row = new Random().nextInt(GRID_HEIGHT);
            int col = new Random().nextInt(GRID_WIDTH);

            if(grid[row][col].getValue() == Tile.BLANK && (row != x && col != y)){
                grid[row][col].setValue(Tile.Mine);
                minesPlaced++;
            }
        }

        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                int count = 0;
                if (grid[row][col].getValue() != Tile.BOMB) { // skip if index is a bomb
                    if ((row > 0) && (col > 0) && (grid[row - 1][col - 1].getValue() == Tile.BOMB)) { // up-left
                        count++;
                    }
                    if (col > 0 && grid[row][col - 1].getValue() == Tile.BOMB) { // up
                        count++;
                    }
                    if (col < GRID_WIDTH - 1 && grid[row][col + 1].getValue() == Tile.BOMB) { // down
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && col > 0 && grid[row + 1][col - 1].getValue() == Tile.BOMB) { // up-right
                        count++;
                    }
                    if (row > 0 && grid[row - 1][col].getValue() == Tile.BOMB) { // left
                        count++;
                    }
                    if (row > 0 && col < GRID_WIDTH - 1 && grid[row - 1][col + 1].getValue() == Tile.BOMB) { // down-left
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && col < GRID_WIDTH - 1 && grid[row + 1][col + 1].getValue() == Tile.BOMB) {// down-right
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && grid[row + 1][col].getValue() == Tile.BOMB) { // right
                        count++;
                    }

                    grid[row][col].setValue(count);
                }

            }
        }
        return grid;
    }


    // returns a 2d Tile array with all indexes(tiles) set to blank
    private static Tile[][] makeBlankGrid(){
        Tile[][] grid = new Tile[GRID_WIDTH][GRID_HEIGHT];
        for (int i = 0; i < GRID_WIDTH; i++){
            for (int j = 0; j < GRID_HEIGHT; j++){
                grid[i][j] = new Tile(Tile.BLANK);
            }
        }
        return grid;
    }

    public int getTileValue(int row, int col){
        return tileGrid[row][col].getValue();
    }

    public boolean isTileHidden(int row, int col){
        return tileGrid[row][col].isHidden();
    }

    public void setTileRevealed(int row, int col) {
        if(tileGrid[row][col].isHidden()) {
            tileGrid[row][col].setRevealed(true);
            revealedTiles++;
        }
        if (row < GRID_WIDTH - 1) {
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
        }
        if (col > 0) {
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
        }
        if (col < GRID_HEIGHT - 1) {
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
        }
    }

    public boolean isGameOver() {
        for(int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if(mLightsGrid[i][j])
                    return false;
            }
        }
        return true;
    }

    public String getState() {
        StringBuilder board = new StringBuilder();
        for(int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                char value = mLightsGrid[i][j] ? 'T' : 'F';
                board.append(value);
            }
        }
        return board.toString();
    }

    public boolean isGameOver() {
        return gameWon || gameLost;
    }
 */


}
