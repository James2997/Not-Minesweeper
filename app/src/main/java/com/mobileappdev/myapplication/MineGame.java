package com.mobileappdev.myapplication;

import java.util.Random;

public class MineGame {
    public static final int GRID_HEIGHT = 10, GRID_WIDTH = 10;
    public static final int totalMines = 15;
    public static int score = 0;

    private Tile[][] tileGrid;
    private boolean gameWon, gameLost;

    public MineGame() {
        tileGrid = new Tile[GRID_HEIGHT][GRID_WIDTH];
    }

    // x and y parameters represent the coordinates of the first clicked tile
    public void newGame(int x, int y) {
        gameLost = false;
        gameWon = false;
        tileGrid = makeBlankGrid(GRID_HEIGHT, GRID_WIDTH);
        int minesPlaced = 0;

        while (minesPlaced < totalMines){
            int row = new Random().nextInt(GRID_HEIGHT);
            int col = new Random().nextInt(GRID_WIDTH);

            if(tileGrid[row][col].getValue() == Tile.BLANK){
                if(!(row == x && col == y)) {           //prevents first tile clicked from being a mine
                    tileGrid[row][col].setValue(Tile.Mine);
                    minesPlaced++;
                }
            }
        }
        tileGrid = calcNeighborBombs(tileGrid);
    }


    // iterates through array and counts surrounding bombs
    private static Tile[][] calcNeighborBombs(Tile[][] grid){

        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                int count = 0;
                if (grid[row][col].getValue() != Tile.Mine) { // skip if index is a bomb
                    if ((row > 0) && (col > 0) && (grid[row - 1][col - 1].getValue() == Tile.Mine)) { // up-left
                        count++;
                    }
                    if (col > 0 && grid[row][col - 1].getValue() == Tile.Mine) { // up
                        count++;
                    }
                    if (col < GRID_WIDTH - 1 && grid[row][col + 1].getValue() == Tile.Mine) { // down
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && col > 0 && grid[row + 1][col - 1].getValue() == Tile.Mine) { // up-right
                        count++;
                    }
                    if (row > 0 && grid[row - 1][col].getValue() == Tile.Mine) { // left
                        count++;
                    }
                    if (row > 0 && col < GRID_WIDTH - 1 && grid[row - 1][col + 1].getValue() == Tile.Mine) { // down-left
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && col < GRID_WIDTH - 1 && grid[row + 1][col + 1].getValue() == Tile.Mine) {// down-right
                        count++;
                    }
                    if (row < GRID_HEIGHT - 1 && grid[row + 1][col].getValue() == Tile.Mine) { // right
                        count++;
                    }

                    grid[row][col].setValue(count);
                }

            }
        }
        return grid;
    }


    // returns a 2d Tile array with all indexes(tiles) set to blank
    public Tile[][] makeBlankGrid(int width, int height){
        Tile[][] grid = new Tile[width][height];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                grid[i][j] = new Tile(Tile.BLANK);
            }
        }
        return grid;
    }

    public void revealAdjacentBlanks(int row, int col){
        if (row < 0 || row > GRID_HEIGHT -1 || col < 0 || col > GRID_WIDTH - 1)
            return;

        if (tileGrid[row][col].getValue() == Tile.Mine || tileGrid[row][col].isRevealed() == true)
            return;

        if (tileGrid[row][col].getValue() == Tile.BLANK) {
            tileGrid[row][col].setRevealed(true);

            revealAdjacentBlanks(row, col - 1);
            revealAdjacentBlanks(row, col + 1);
            revealAdjacentBlanks(row - 1, col);
            revealAdjacentBlanks(row + 1, col);
            revealAdjacentBlanks(row + 1, col + 1);
            revealAdjacentBlanks(row - 1, col - 1);
            revealAdjacentBlanks(row + 1, col - 1);
            revealAdjacentBlanks(row - 1, col + 1);

            // if statements used to reveal the number tiles that surround blank tiles
            if ((row > 0) && (col > 0) && (tileGrid[row - 1][col - 1].getValue() != Tile.Mine)) { // up-left
                tileGrid[row -1][col -1].setRevealed(true);
            }
            if (col > 0 && tileGrid[row][col - 1].getValue() != Tile.Mine) { // up
                tileGrid[row][col - 1].setRevealed(true);
            }
            if (col < GRID_WIDTH - 1 && tileGrid[row][col + 1].getValue() != Tile.Mine) { // down
                tileGrid[row][col + 1].setRevealed(true);
            }
            if (row < GRID_HEIGHT - 1 && col > 0 && tileGrid[row + 1][col - 1].getValue() != Tile.Mine) { // up-right
                tileGrid[row + 1][col - 1].setRevealed(true);
            }
            if (row > 0 && tileGrid[row - 1][col].getValue() == Tile.Mine) { // left
                tileGrid[row - 1][col].setRevealed(true);
            }
            if (row > 0 && col < GRID_WIDTH - 1 && tileGrid[row - 1][col + 1].getValue() != Tile.Mine) { // down-left
                tileGrid[row - 1][col + 1].setRevealed(true);
            }
            if (row < GRID_HEIGHT - 1 && col < GRID_WIDTH - 1 && tileGrid[row + 1][col + 1].getValue() != Tile.Mine) {// down-right
                tileGrid[row + 1][col + 1].setRevealed(true);
            }
            if (row < GRID_HEIGHT - 1 && tileGrid[row + 1][col].getValue() != Tile.Mine) { // right
                tileGrid[row + 1][col].setRevealed(true);
            }
        }
    }

    public int countRevealedTiles(){
        int count = 0;
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                if (tileGrid[row][col].isRevealed())
                    count++;
            }
        }
        return count;
    }

    public int getTileValue(int row, int col){
        return tileGrid[row][col].getValue();
    }

    public boolean isTileRevealed(int row, int col){
        return tileGrid[row][col].isRevealed();
    }

    public void setTileRevealed(int row, int col) {
        if(tileGrid[row][col].isRevealed()) {
            tileGrid[row][col].setRevealed(true);
        }

        if(countRevealedTiles() == (GRID_HEIGHT * GRID_WIDTH) - totalMines) {
            gameWon = true;
        }

        if (tileGrid[row][col].getValue() == Tile.Mine){
            tileGrid[row][col].setValue(-2);
            gameLost = true;
        }
    }

    public boolean setFlag(int row, int col) {
        tileGrid[row][col].setFlagged(!tileGrid[row][col].isFlagged());
        return tileGrid[row][col].isFlagged();
    }

    public boolean isFlagged(int row, int col) {
        return tileGrid[row][col].isFlagged();
    }

    public boolean isGameOver() {
        return gameWon || gameLost;
    }

    public boolean isGameWon() {
        if(countRevealedTiles() == (GRID_HEIGHT * GRID_WIDTH) - totalMines) {
            gameWon = true;
       }
        return gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }
}
