package com.mobileappdev.myapplication;

import java.util.Random;

public class MineGame {
    public static final int GRID_WIDTH = 6, GRID_HEIGHT = 10;

    private final boolean[][] mLightsGrid;

    public MineGame() {
        mLightsGrid = new boolean[GRID_WIDTH][GRID_HEIGHT];
    }

    public void newGame() {
        Random ran = new Random();
        for(int i = 0; i < GRID_WIDTH; i++) {
            for(int j = 0; j < GRID_HEIGHT; j++) {
                mLightsGrid[i][j] = ran.nextBoolean();
            }
        }
    }

    public void selectLight(int row, int col) {
        mLightsGrid[row][col] = !mLightsGrid[row][col];
        if (row > 0) {
            mLightsGrid[row - 1][col] = !mLightsGrid[row - 1][col];
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

    public void setState(String gameState) {
        int index = 0;
        for(int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                mLightsGrid[i][j] = gameState.charAt(index) == 'T';
                index++;
            }
        }
    }
}
