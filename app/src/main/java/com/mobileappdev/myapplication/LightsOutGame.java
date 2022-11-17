package com.mobileappdev.myapplication;

import java.util.Random;

public class LightsOutGame {
    public static final int GRID_SIZE = 3;

    private final boolean[][] mLightsGrid;

    public LightsOutGame() {
        mLightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    public void newGame() {
        Random ran = new Random();
        for(int i = 0; i < GRID_SIZE; i++) {
            for(int j = 0; j < GRID_SIZE; j++) {
                mLightsGrid[i][j] = ran.nextBoolean();
            }
        }
    }

    public boolean isLightOn(int row, int col) {
        return mLightsGrid[row][col];
    }

    public void selectLight(int row, int col) {
        mLightsGrid[row][col] = !mLightsGrid[row][col];
        if (row > 0) {
            mLightsGrid[row - 1][col] = !mLightsGrid[row - 1][col];
        }
        if (row < GRID_SIZE - 1) {
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
        }
        if (col > 0) {
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
        }
    }

    public void cheat() {
        for(int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                mLightsGrid[i][j] = false;
            }
        }
    }

    public boolean isGameOver() {
        for(int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if(mLightsGrid[i][j])
                    return false;
            }
        }
        return true;
    }

    public String getState() {
        StringBuilder board = new StringBuilder();
        for(int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                char value = mLightsGrid[i][j] ? 'T' : 'F';
                board.append(value);
            }
        }
        return board.toString();
    }

    public void setState(String gameState) {
        int index = 0;
        for(int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                mLightsGrid[i][j] = gameState.charAt(index) == 'T';
                index++;
            }
        }
    }
}
